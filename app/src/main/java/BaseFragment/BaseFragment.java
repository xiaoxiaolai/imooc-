package BaseFragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.xiao.cui.R;
import com.example.xiao.cui.interface_.ToolBar;

public class BaseFragment extends Fragment
{
	private String mTitle = "Default";

	public static final String TITLE = "title";

	public static final String CONTEXT = "context";

    public ToolBar toolBar;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
		toolBar=(ToolBar) getActivity();
		if (getArguments() != null)
		{
			mTitle = getArguments().getString(TITLE);
		}
View view = inflater.inflate(R.layout.activity_main2,container,false);
		toolBar.initToolbar((Toolbar) view.findViewById(R.id.toolbar2));

		TextView tv = new TextView(getActivity());
		tv.setTextSize(20);
		tv.setBackgroundColor(Color.parseColor("#ffffffff"));
		tv.setText(mTitle);
		tv.setGravity(Gravity.CENTER);
		return view;

	}
}
