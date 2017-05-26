package kr.co.edu_a.mooil;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {
    boolean isCheckBoxDraw = false;
    boolean isFavoriteDraw = true;
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<ListViewItem> listViewItemList = new ArrayList<ListViewItem>() ;
    // ListViewAdapter의 생성자
    public ListViewAdapter() {
    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();
        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_listview, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        CheckBox listCheckBox = (CheckBox) convertView.findViewById((R.id.listCheckBox));
        ImageView iconImageView = (ImageView) convertView.findViewById(R.id.listFileImage1) ;
        LinearLayout textLayout = (LinearLayout) convertView.findViewById(R.id.layoutListText);
        TextView titleTextView = (TextView) convertView.findViewById(R.id.listFileText1) ;
        TextView descTextView = (TextView) convertView.findViewById(R.id.listFileText2) ;
        ImageView favorImageView = (ImageView) convertView.findViewById(R.id.listFavorImage01) ;
        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        ListViewItem listViewItem = listViewItemList.get(position);
        listCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                                                        listViewItemList.get(pos).setCheck(isChecked);
                                                } });

        favorImageView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                if(listViewItemList.get(pos).getFavor() == 0)
                {
                    listViewItemList.get(pos).setFavor(1);
                    FavoriteList.addList(listViewItemList.get(pos).getPath());
                }
                else
                {
                    listViewItemList.get(pos).setFavor(0);
                    FavoriteList.remove(listViewItemList.get(pos).getPath());
                }
                refreshItems();
            }
        });

        // 아이템 내 각 위젯에 데이터 반영
        listCheckBox.setChecked(listViewItem.getCheck());
        iconImageView.setImageDrawable(listViewItem.getIcon());
        titleTextView.setText(listViewItem.getTitle());
        descTextView.setText(listViewItem.getDesc());
        if(listViewItem.getFavor()==0)
            favorImageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.list_favor_off));
        if(listViewItem.getFavor()==1)
            favorImageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.list_favor_on));
        if(listViewItem.getFavor()==2)
            favorImageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.list_null));

        if(isCheckBoxDraw){
            listCheckBox.setVisibility(View.VISIBLE);
            textLayout.setWeightSum(4);
        }
        else{
            listCheckBox.setVisibility(View.GONE);
            textLayout.setWeightSum(5);
        }
        if(isFavoriteDraw){
            favorImageView.setVisibility(View.VISIBLE);
        }
        else{
            favorImageView.setVisibility(View.GONE);
        }

        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position) ;
    }
    public void refreshItems()
    {
        this.notifyDataSetChanged();
    }
    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(Drawable icon, String title, String path, String desc, int favor) {
        ListViewItem item = new ListViewItem();

        item.setCheck(false);
        item.setIcon(icon);
        item.setTitle(title);
        item.setDesc(desc);
        item.setFavor(favor);
        item.setPath(path);
        listViewItemList.add(item);
    }
    public boolean getChecker(int position) {
        return listViewItemList.get(position).getCheck() ;
    }
    public void setChecker(int position) {
        listViewItemList.get(position).setCheck(getChecker(position) ? false : true);
    }

    public void clearAll()
    {
        listViewItemList.clear();
    }

    public void setCheckBoxVisibility(boolean isVisibility){
        isCheckBoxDraw = isVisibility;
    }

    public void checkBoxChangedListener(int position, boolean isCheck){ listViewItemList.get(position).setCheck(isCheck); }
}