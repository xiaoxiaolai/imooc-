
package modle;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xiao.cui.R;

public class PinnedHeaderExpandableAdapter implements ExpandableListAdapter {
    private Context context;

    public PinnedHeaderExpandableAdapter(String[] armTypes, String[][] arms, Context context) {
        this.armTypes = armTypes;
        this.arms = arms;
        this.context = context;
    }
    public PinnedHeaderExpandableAdapter(Context context) {

        this.context = context;
        inflater = LayoutInflater.from(context);
    }
    private LayoutInflater inflater;




    private String[] armTypes = new String[]{
            "WOR文档编辑D", "文档排版文档排版 ", "EM文档编辑AIL", "文档编辑PPT"
    };
    private String[][] arms = new String[][]{
            {"文档编辑", "文档排版", "文档处理", "文档打印"},
            {"表格编辑", "表格排版", "表格处理", "表格打印"},
            {"收发邮件", "管理邮箱", "登录登出", "注册绑定"},
            {"演示编辑", "演示排版", "演示处理", "演示打印"},
    };

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getGroupCount() {
        return armTypes.length;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return arms[groupPosition].length;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return armTypes[groupPosition];
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return arms[groupPosition][childPosition];
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
            convertView = inflater.inflate(R.layout.group, null);
            groupHolder.textView = (TextView) convertView
                    .findViewById(R.id.group);
            groupHolder.imageView = (ImageView) convertView
                    .findViewById(R.id.image);
            convertView.setTag(groupHolder);
        } else {
            groupHolder = (GroupHolder) convertView.getTag();
        }

        groupHolder.textView.setText((String) getGroup(groupPosition));
//        if (isExpanded)// ture is Expanded or false is not isExpanded
//            groupHolder.imageView.setImageResource(R.mipmap.expanded);
//        else
//            groupHolder.imageView.setImageResource(R.mipmap.collapse);
        return convertView;
    }


    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
//        TextView textView = getTextView();
//        textView.setText(getChild(groupPosition, childPosition).toString());
//        return textView;
        ChildHolder childHolder = null;
        if (convertView == null) {
            childHolder = new ChildHolder();
            convertView = inflater.inflate(R.layout.download_list_item_group_child, null);
            childHolder.textView = (TextView) convertView.findViewById(R.id.group_child);
//            childHolder.size = (TextView) convertView
//                    .findViewById(R.id.size);
//            groupHolder.imageView = (ImageView) convertView
//                    .findViewById(R.id.image);
            convertView.setTag(childHolder);
        } else {
            childHolder = (ChildHolder) convertView.getTag();
        }
//        ChapterInfo.DataBean.MediaBean mediaBean=(ChapterInfo.DataBean.MediaBean)getChild(groupPosition, childPosition);
        childHolder.textView.setText(getChild(groupPosition, childPosition).toString());
//        childHolder.size.setText(Utils.getGBSize(mediaBean.getMedia_down_size()));
//        if (isExpanded)// ture is Expanded or false is not isExpanded
//            groupHolder.imageView.setImageResource(R.mipmap.expanded);
//        else
//            groupHolder.imageView.setImageResource(R.mipmap.collapse);
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

    private TextView getTextView() {
        AbsListView.LayoutParams lp = new AbsListView.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 64);
        TextView textView = new TextView(context);
        textView.setLayoutParams(lp);
        textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
//        textView.setPadding(36, 10, 10, 10);
        textView.setPadding(36, 10, 10, 10);
        textView.setTextSize(20);
        return textView;
    }

    class GroupHolder {
        TextView textView;
        ImageView imageView;
    }
    class ChildHolder {
        TextView textView;
        TextView size;
        ImageView imageView;
    }
}