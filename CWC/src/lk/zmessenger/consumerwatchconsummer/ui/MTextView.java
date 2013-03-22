package lk.zmessenger.consumerwatchconsummer.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class MTextView extends TextView {

	public MTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
		rotate();
	}

	public MTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
		rotate();
	}

	public MTextView(Context context) {
		super(context);
		init();
		rotate();
	}

	private void rotate() {
		// TODO Auto-generated method stub
		setSelected(true);
	}

	private void init() {
		if (!isInEditMode()) {

		}
	}

}
