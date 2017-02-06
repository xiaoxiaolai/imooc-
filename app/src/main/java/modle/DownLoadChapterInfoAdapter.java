
package modle;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xiao.cui.R;

import java.util.ArrayList;
import java.util.List;

import entity.ChapterInfo;
import entity.DownLoadChapterInfo;
import utils.Utils;

import static ui.MyAdapter.TAG;

public class DownLoadChapterInfoAdapter extends BaseExpandableListAdapter {
    private Context context;
    public List<Long> isCheckMap = new ArrayList();
    private List<DownLoadChapterInfo> chapterInfos;
    private List<Long> list = new ArrayList();

    public DownLoadChapterInfoAdapter(List<ChapterInfo.DataBean> armTypes, List<DownLoadChapterInfo> chapterInfos, Context context) {
        this.armTypes = armTypes;
        this.chapterInfos = chapterInfos;
        if (chapterInfos != null && chapterInfos.size() != 0) {
            for (int i = 0; i < chapterInfos.size(); i++) {
                Log.d(TAG, "onResponse:--------------------------------------------- " + chapterInfos.get(i).getOrder());
                list.add(Long.valueOf(chapterInfos.get(i).getOrder()));
            }
        }
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public DownLoadChapterInfoAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    private LayoutInflater inflater;


    private List<ChapterInfo.DataBean> armTypes;
    private List<ChapterInfo.DataBean.MediaBean> arms;

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
        return armTypes.get(groupPosition).getMedia().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return armTypes.get(groupPosition).getChapter();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return armTypes.get(groupPosition).getMedia().get(childPosition);
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
            convertView = inflater.inflate(R.layout.download_list_item_group, null);
            groupHolder.textView = (TextView) convertView
                    .findViewById(R.id.group);
//            groupHolder.imageView = (ImageView) convertView
//                    .findViewById(R.id.image);
            convertView.setTag(groupHolder);
        } else {
            groupHolder = (GroupHolder) convertView.getTag();
        }

        groupHolder.textView.setText(((ChapterInfo.DataBean.ChapterBean) getGroup(groupPosition)).getName());
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
        Integer integer = childPosition + groupPosition * 100;
        ChildHolder childHolder = null;
        if (convertView == null) {
            childHolder = new ChildHolder();
            convertView = inflater.inflate(R.layout.download_list_item_group_child, null);
            childHolder.textView = (TextView) convertView
                    .findViewById(R.id.group_child);
            childHolder.size = (TextView) convertView
                    .findViewById(R.id.size);
            childHolder.imageView = (ImageView) convertView
                    .findViewById(R.id.imageview);
            childHolder.checkBtn = (CheckBox) convertView.findViewById(R.id.checkBox);
            convertView.setTag(childHolder);
        } else {
            childHolder = (ChildHolder) convertView.getTag();
        }

        int i = childPosition + groupPosition * 10;
        childHolder.checkBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Long radiaoId = (Long) buttonView.getTag();
                if (isChecked) {
                    //将选中的放入hashmap中
                    if (isCheckMap != null && !isCheckMap.contains(radiaoId) ){
                        isCheckMap.add(radiaoId);
                    }

                } else {
                    //取消选中的则剔除
                    isCheckMap.remove(radiaoId);
                }
            }
        });
        childHolder.checkBtn.setTag(Long.valueOf(childPosition + groupPosition * 100));
        ChapterInfo.DataBean.MediaBean mediaBean = (ChapterInfo.DataBean.MediaBean) getChild(groupPosition, childPosition);
        childHolder.textView.setText(getName(armTypes.get(groupPosition), childPosition));
        childHolder.size.setText(Utils.getGBSize(mediaBean.getMedia_down_size()));
//        if (isExpanded)// ture is Expanded or false is not isExpanded
//            groupHolder.imageView.setImageResource(R.mipmap.expanded);
//        else
//            groupHolder.imageView.setImageResource(R.mipmap.collapse);
        //找到需要选中的条目
        if (isCheckMap != null && isCheckMap.contains(Long.valueOf(childPosition + groupPosition * 100))) {
            childHolder.checkBtn.setChecked(true);
        } else {
            childHolder.checkBtn.setChecked(false);

        }
//        if (list.size() > 0 && list != null) {
            if (list.contains(Long.valueOf(childPosition + groupPosition * 100))) {

                childHolder.checkBtn.setVisibility(View.INVISIBLE);
                childHolder.imageView.setVisibility(View.VISIBLE);
                childHolder.checkBtn.setChecked(false);
            } else {
                childHolder.checkBtn.setVisibility(View.VISIBLE);
                childHolder.imageView.setVisibility(View.INVISIBLE);
            }
//        }
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
        textView.setPadding(36, 0, 0, 0);
        textView.setTextSize(20);
        return textView;
    }

    public void checkAllBox() {
        isCheckMap.clear();
        for (int i = 0; i < getGroupCount(); i++) {
            for (int j = 0; j < getChildrenCount(i); j++) {
                isCheckMap.add(Long.valueOf(j + i * 100));
            }
        }
        Toast.makeText(context," "+isCheckMap.size(),Toast.LENGTH_SHORT).show();
        if (list.size() > 0) {
            Toast.makeText(context," "+list.size(),Toast.LENGTH_SHORT).show();
            isCheckMap.removeAll(list);
        }
        Toast.makeText(context," "+isCheckMap.size(),Toast.LENGTH_SHORT).show();
    }

    private String getName(ChapterInfo.DataBean bean, int childPosition) {

        return bean.getChapter().getSeq() + "-" + bean.getMedia().get(childPosition).getMedia_seq() + " " + bean.getMedia().get(childPosition).getName();

    }

    public void checkAllBoxCancle() {
        isCheckMap.clear();
    }

    class GroupHolder {
        TextView textView;
        ImageView imageView;
    }

    class ChildHolder {
        TextView textView;
        TextView size;
        ImageView imageView;
        CheckBox checkBtn;
    }
}