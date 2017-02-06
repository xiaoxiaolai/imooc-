package ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;


public class LoadListView extends ListView implements AbsListView.OnScrollListener {
	View footer;// 底部布局；
	int totalItemCount;// 总数量；
	int lastVisibleItem;// 最后一个可见的item；
	boolean isLoading;// 正在加载；
	ILoadListener iLoadListener;
	private IDownListener iDownListener;

	public LoadListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initView(context);
	}

	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int mExpandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, mExpandSpec);
	}
	public LoadListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initView(context);
	}

	public LoadListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		initView(context);
	}

	/**
	 * 添加底部加载提示布局到listview
	 *
	 * @param context
	 */
	private void initView(Context context) {
		LayoutInflater inflater = LayoutInflater.from(context);
//		footer = inflater.inflate(R.layout.footer_layout, null);
////		footer.findViewById(R.id.load_layout).setVisibility(View.GONE);
//		this.addFooterView(footer);
		this.setOnScrollListener(this);
	}

//	@Override
//	public boolean onInterceptTouchEvent(MotionEvent ev) {
//		return true;
//	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
						 int visibleItemCount, int totalItemCount) {
		if(onScrollListener!=null)
			onScrollListener.onScrollStateChanged(view,scrollState);
//		onScrollListener.onScroll(view,firstVisibleItem,visibleItemCount,totalItemCount);
		// TODO Auto-generated method stub
		Log.d(TAG, " LoadListView onScroll: ---------------------------");
		this.lastVisibleItem = firstVisibleItem + visibleItemCount;
		this.totalItemCount = totalItemCount;
		if (totalItemCount == lastVisibleItem) {
//			footer.findViewById(R.id.load_layout).setVisibility(
//					View.VISIBLE);}
		}

	}
private int scrollState=0;
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		this.scrollState=scrollState;
		if(onScrollListener!=null)
		onScrollListener.onScrollStateChanged(view,scrollState);
		// TODO Auto-generated method stub
		Log.d(TAG, " LoadListView onScrollStateChanged: ---------------------------");


	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {


		return super.onTouchEvent(ev);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		Log.d(TAG, " LoadListView dispatchTouchEvent: ---------------------------");
		return super.dispatchTouchEvent(ev);
	}

	/**
	 * 加载完毕
	 */
	public void loadComplete(){
		isLoading = false;
//		footer.findViewById(R.id.load_layout).setVisibility(
//				View.GONE);
	}

	private String TAG=" LoadListView ";
//	@Override
//	public boolean onInterceptTouchEvent(MotionEvent ev) {
////		Log.d(TAG, " LoadListView : ---------------------------");)&&(totalItemCount == (lastVisibleItem-1))&&(scrollState!=1)
//		if (lastVisibleItem-1>0&&(totalItemCount == (lastVisibleItem-1))) {
//			footer.findViewById(R.id.load_layout).setVisibility(
//					View.VISIBLE);
//			if (!isLoading) {
//				isLoading = true;
//
//				// 加载更多
//				Toast.makeText(getContext(),"11111111",Toast.LENGTH_SHORT).show();
////				iDownListener
//				iLoadListener.onLoad(this);
////				loadComplete();
//			}
//		}
//
//		return true;
//	}



//	public void setInterface(ILoadListener iLoadListener,IDownListener iDownListener){
//		this.iDownListener = iDownListener;
//		this.iLoadListener = iLoadListener;
//	}

	public void setInterface(ILoadListener iLoadListener){
		this.iLoadListener = iLoadListener;
	}
	private OnScrollListener onScrollListener ;

	public void setOnScrollListener(OnScrollListener onScrollListener){
		this.onScrollListener=onScrollListener;
}
	public void setDownnterface(IDownListener iDownListener){
		this.iDownListener = iDownListener;
	}
	//加载更多数据的回调接口
	public interface ILoadListener{
		public void onLoad(LoadListView view);
	}

	public interface IDownListener{
		public void onDown(Boolean aBoolean);
	}

public interface OnScrollListener{
	int SCROLL_STATE_FLING = 2;
	int SCROLL_STATE_IDLE = 0;
	int SCROLL_STATE_TOUCH_SCROLL = 1;

	void onScrollStateChanged(AbsListView view, int scrollState);

	void onScroll(AbsListView view, int firstVisibleItem,
				  int visibleItemCount, int totalItemCount);

}
}
