package com.ws.component;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.ws.base.R;

/**
 * 描述信息
 *
 * @author ws
 * @date 12/21/20 3:52 PM
 * 修改人：ws
 */
public class MyCodeEditText extends ConstraintLayout
{


    private static final String TAG = "CodeEditText";
    //LayoutCodeEditBinding binding;
    View     view;
    public EditText et1, et2, et3, et4, et5, et6;
    private Listener listener;

    public MyCodeEditText(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.layout_code_edit, this);
        //view.setTag("layout/layout_code_edit_0");
        //binding = DataBindingUtil.bind(view);
        et1 = view.findViewById(R.id.et1);
        et2 = view.findViewById(R.id.et2);
        et3 = view.findViewById(R.id.et3);
        et4 = view.findViewById(R.id.et4);
        et5 = view.findViewById(R.id.et5);
        et6 = view.findViewById(R.id.et6);
        initViews();
    }


    private void initViews()
    {
        OnFocusChangeListener onFocusChangeListener = (view, b) ->
        {
            if (b) ((EditText) view).setText("");
        };
        et1.setOnFocusChangeListener(onFocusChangeListener);
        et2.setOnFocusChangeListener(onFocusChangeListener);
        et3.setOnFocusChangeListener(onFocusChangeListener);
        et4.setOnFocusChangeListener(onFocusChangeListener);
        et5.setOnFocusChangeListener(onFocusChangeListener);

        et6.setOnFocusChangeListener((view, b) ->
        {
            if (b)
            {
                et6.setText("");
            }
        });

        et1.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                if (editable.length() == 1)
                {
                    et2.requestFocus();
                    et2.setText("");
                    checkAndCommit();
                }
            }
        });

        et2.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                if (editable.length() == 1)
                {
                    et3.requestFocus();
                    et3.setText("");
                    checkAndCommit();
                }
            }
        });
        et2.setOnKeyListener(new OnKeyListener()
        {
            @Override
            public synchronized boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (keyCode == KeyEvent.KEYCODE_DEL)
                {
                    et1.requestFocus();
                    et1.setText("");
                }
                return false;
            }
        });
        et3.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                if (editable.length() == 1)
                {
                    et4.requestFocus();
                    et4.setText("");
                    checkAndCommit();
                }
            }
        });
        et3.setOnKeyListener(new OnKeyListener()
        {
            @Override
            public synchronized boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (keyCode == KeyEvent.KEYCODE_DEL)
                {
                    et2.requestFocus();
                    et2.setText("");
                }
                return false;
            }
        });
        et4.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                if (editable.length() == 1)
                {
                    et5.requestFocus();
                    et5.setText("");
                    checkAndCommit();
                }
            }
        });
        et4.setOnKeyListener(new OnKeyListener()
        {
            @Override
            public synchronized boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (keyCode == KeyEvent.KEYCODE_DEL)
                {
                    et3.requestFocus();
                    et3.setText("");
                }
                return false;
            }
        });
        et5.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                if (editable.length() == 1)
                {
                    et6.requestFocus();
                    et6.setText("");
                    checkAndCommit();
                }
            }
        });
        et5.setOnKeyListener(new OnKeyListener()
        {
            @Override
            public synchronized boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (keyCode == KeyEvent.KEYCODE_DEL)
                {
                    et4.requestFocus();
                    et4.setText("");
                }
                return false;
            }
        });
        et6.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                if (editable.length() == 1)
                {
                    checkAndCommit();
                }
            }
        });
        et6.setOnKeyListener(new OnKeyListener()
        {
            @Override
            public synchronized boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (keyCode == KeyEvent.KEYCODE_DEL)
                {
                    et5.requestFocus();
                    et5.setText("");
                }
                return false;
            }
        });
    }


    public void checkAndCommit()
    {
        StringBuilder stringBuilder = new StringBuilder();
        String content = et1.getText().toString();
        if (content.length() == 0)
        {
            return;
        }
        stringBuilder.append(content);
        content = et2.getText().toString();
        if (content.length() == 0)
        {
            return;
        }
        stringBuilder.append(content);
        content = et3.getText().toString();
        if (content.length() == 0)
        {
            return;
        }
        stringBuilder.append(content);
        content = et4.getText().toString();
        if (content.length() == 0)
        {
            return;
        }
        stringBuilder.append(content);
        content = et5.getText().toString();
        if (content.length() == 0)
        {
            return;
        }
        stringBuilder.append(content);
        content = et6.getText().toString();
        if (content.length() == 0)
        {
            return;
        }
        stringBuilder.append(content);
   /*     for (int i = 0; i < 6; i++)
        {
            if (getChildAt(i) instanceof EditText)
            {
                EditText editText = (EditText) getChildAt(i);
                String content = editText.getText().toString();
                if (content.length() == 0)
                {
                    full = false;
                    break;
                } else
                {
                    stringBuilder.append(content);
                }
            }

        }*/
        Log.d(TAG, "checkAndCommit:" + stringBuilder.toString());

        if (listener != null)
        {
            listener.onComplete(stringBuilder.toString());
            setEnabled(false);
        }


    }


    public void setOnCompleteListener(Listener listener)
    {
        this.listener = listener;
    }


    public interface Listener
    {
        void onComplete(String content);
    }

}
