
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
import java.util.List;

import entity.Project;

public class CoursesDetails1PinnedHeaderExpandableAdapter implements ExpandableListAdapter {
    private List<Project.DataBean> armTypes =new ArrayList<>();
    private Context context;

    public CoursesDetails1PinnedHeaderExpandableAdapter(List<Project.DataBean> armTypes,  Context context) {
        this.armTypes = armTypes;
        this.context = context;
        inflater = LayoutInflater.from(context);
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
        return armTypes.get(groupPosition).getCourse().get(childPosition);
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
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        GroupHolder groupHolder = null;
        if (convertView == null) {
            groupHolder = new GroupHolder();
            convertView = inflater.inflate(R.layout.coursesdetails1_group, null);
            groupHolder.textView1 = (TextView) convertView.findViewById(R.id.textView1);
            groupHolder.textView2 = (TextView) convertView.findViewById(R.id.textView2);
            convertView.setTag(groupHolder);
        } else {
            groupHolder = (GroupHolder) convertView.getTag();
        }

        groupHolder.textView1.setText(armTypes.get(groupPosition).getName());
        groupHolder.textView2.setText(armTypes.get(groupPosition).getDescription());
        return convertView;
    }


    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildHolder childHolder = null;
        if (convertView == null) {
            childHolder = new ChildHolder();
            convertView = inflater.inflate(R.layout.coursesdetails1_item_item, null);
            childHolder.viewGroup = (ViewGroup) convertView.findViewById(R.id.linearLayout);
            convertView.setTag(childHolder);
        } else {
            childHolder = (ChildHolder) convertView.getTag();
        }
        childHolder.viewGroup.removeAllViews();

        for (int i = 0; i < armTypes.get(groupPosition).getCourse().size(); i++) {

            View view = inflater.inflate(R.layout.coursesdetails1_item, null);
            ChildHolderItem item =new ChildHolderItem();
            item.imageView= (ImageView) view.findViewById(R.id.imageview);
            item.textView= (TextView) view.findViewById(R.id.textView);
            item.textView.setText(armTypes.get(groupPosition).getCourse().get(i).getTitle());
            final int finalJ = i;
            item.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context,""+ groupPosition + childPosition + finalJ,Toast.LENGTH_SHORT).show();
                    Log.d("----------------------", ""+ groupPosition + childPosition);
                }
            });
            Glide.with(context).load(armTypes.get(groupPosition).getCourse().get(i).getImg())
                    .dontAnimate()
//                .placeholder(R.mipmap.ic_launcher)
//                .skipMemoryCache(true)
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .transform(new BitmapCircleTransformation(mContext))
                    .error(R.mipmap.ic_launcher)
                    .into( item.imageView);
            childHolder.viewGroup.addView(view);
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