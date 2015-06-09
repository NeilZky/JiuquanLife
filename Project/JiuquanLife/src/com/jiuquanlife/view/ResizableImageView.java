package com.jiuquanlife.view;

import java.text.AttributedCharacterIterator.Attribute;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

public class ResizableImageView extends ImageView
{

    private Bitmap mBitmap;

    // Constructor

    public ResizableImageView(Context context)
    {
        super(context);
    }
    
    

    // Overriden methods


      public ResizableImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}



	public ResizableImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}



	@Override 
      protected void onMeasure(int widthMeasureSpec,
              int heightMeasureSpec) {
          if(mBitmap != null)
          {
                int width = MeasureSpec.getSize(widthMeasureSpec);
                int height = width * mBitmap.getHeight() / mBitmap.getWidth();
                View parent = (View) this.getParent();
                System.out.println(parent.getWidth());
                System.out.println(parent.getHeight());
                setMeasuredDimension(width, height);

          } 
          else
          {
              super.onMeasure(widthMeasureSpec,
                      heightMeasureSpec);
          }
          }

        @Override
        public void setImageBitmap(Bitmap bitmap)
        {
            mBitmap = bitmap;
             super.setImageBitmap(bitmap);
        }

}