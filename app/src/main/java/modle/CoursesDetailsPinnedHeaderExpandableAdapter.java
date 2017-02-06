
package modle;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.xiao.cui.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ui.FlowLayout;
import ui.PinnedHeaderExpandableListView;

public class CoursesDetailsPinnedHeaderExpandableAdapter implements ExpandableListAdapter {
    private List<entity.CoursesDetails.DataBean> armTypes =new ArrayList<>();
    private Context context;
    private int index=0;
    private int group=0;
    private PinnedHeaderExpandableListView listView;
    private Map<Integer,Integer> map;

    public CoursesDetailsPinnedHeaderExpandableAdapter(List<entity.CoursesDetails.DataBean> armTypes, Context context, PinnedHeaderExpandableListView listView) {
        this.armTypes = armTypes;
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.listView=listView;
        map=new HashMap<>();
        map.put(0,0);
    }
    private LayoutInflater inflater;
    
    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getGroupCount() {
        return armTypes.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return armTypes.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        GroupHolder groupHolder = null;
        if (convertView == null) {
            groupHolder = new GroupHolder();
            convertView = inflater.inflate(R.layout.coursesdetails_group, null);
            groupHolder.textView1 = (TextView) convertView.findViewById(R.id.textView1);
            groupHolder.layout= (FlowLayout) convertView.findViewById(R.id.flowlayout);
            convertView.setTag(groupHolder);
        } else {
            groupHolder = (GroupHolder) convertView.getTag();
        }

        groupHolder.textView1.setText(armTypes.get(groupPosition).getName());
        groupHolder.layout.removeAllViews();
        TextView tv;
        for (int i = 0; i <armTypes.get(groupPosition).getChildren().size() ; i++) {
             tv = (TextView) inflater.inflate(R.layout.tv,
                    groupHolder.layout, false);
            tv.setText(armTypes.get(groupPosition).getChildren().get(i).getName());
            if(map.containsKey(groupPosition)){
                if (map.get(groupPosition)==i){
                    tv.setTextColor(context.getResources().getColor(R.color.red_7FF01400));
                }

            }
            tv.setTag(i);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int i= (int) v.getTag();
                    group=groupPosition;
                    index=i;
                    map.put(groupPosition,i);
                    listView.collapseGroup(groupPosition);
                    listView.expandGroup(groupPosition);
                }
            });
            groupHolder.layout.addView(tv);

        }

        return convertView;
    }


    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildHolder childHolder = null;
        if (convertView == null) {
            childHolder = new ChildHolder();
            convertView = inflater.inflate(R.layout.coursesdetails_item_item, null);
            childHolder.viewGroup = (ViewGroup) convertView.findViewById(R.id.linearLayout);
            convertView.setTag(childHolder);
        } else {
            childHolder = (ChildHolder) convertView.getTag();
        }
        childHolder.viewGroup.removeAllViews();
        if(map.containsKey(groupPosition)) {

            for (int i = 0; i < armTypes.get(groupPosition).getChildren().get(map.get(groupPosition)).getCourses().size(); i++) {

                View view = inflater.inflate(R.layout.coursesdetails_item, null);
                ChildHolderItem item = new ChildHolderItem();
                item.imageView = (ImageView) view.findViewById(R.id.imageview);
                item.textView = (TextView) view.findViewById(R.id.textView);
                item.textView.setText(armTypes.get(groupPosition).getChildren().get(map.get(groupPosition)).getCourses().get(i).getTitle());
                final int finalJ = i;
                item.textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "" + groupPosition + childPosition + finalJ, Toast.LENGTH_SHORT).show();
                        Log.d("----------------------", "" + groupPosition + childPosition);
                    }
                });
                Glide.with(context).load(armTypes.get(groupPosition).getChildren().get(map.get(groupPosition)).getCourses().get(i).getImg())
                        .dontAnimate()
//                .placeholder(R.mipmap.ic_launcher)
//                .skipMemoryCache(true)
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .transform(new BitmapCircleTransformation(mContext))
                        .error(R.mipmap.ic_launcher)
                        .into(item.imageView);
                childHolder.viewGroup.addView(view);
            }

        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {

    }

    @Override
    public void onGroupCollapsed(int groupPosition) {

    }

    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return 0;
    }

    @Override
    public long getCombinedGroupId(long groupId) {
        return 0;
    }


    class GroupHolder {
        FlowLayout layout;
        TextView textView1;
        TextView textView2;
        ImageView imageView;
    }
    class ChildHolder {
        ViewGroup viewGroup;
        TextView textView;
        TextView size;
        ImageView imageView;
    }
    class ChildHolderItem {
        ViewGroup viewGroup;
        TextView textView;
        TextView size;
        ImageView imageView;
    }
}