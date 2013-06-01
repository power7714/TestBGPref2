package com.example.testbgpref2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.preference.DialogPreference;
import android.preference.Preference;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class BackgroundGalleryPreference extends DialogPreference {
	
	 private static final String androidns="http://schemas.android.com/apk/res/android";
	
	/**
	 * Variables
	 */
	 private Gallery mGallery;
	 private Context mContext;
	 private LinearLayout layout;
	 private int mValue = 0;
	 private String mDependancy="false";
	 private boolean mDependVar;
	 // background names
	 private String[] mBackgroundNames = {
     		"Test One",
     		"Test Two",
     		"Test Three",
     		"Test Four",
     		"Test Five",
     		"Test Six",
     		"Test Seven",
     		"Test Eight",
     		"Test Nine"
     };
	  
	/**
	 * Constructor
	 * @param context
	 * @param attrs
	 */
	@SuppressWarnings("static-access")
	public BackgroundGalleryPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		mContext = context;
		
		mDependancy = attrs.getAttributeValue(this.androidns, "Dependancy");
		
		if(mDependancy != null)
			mDependVar = Boolean.getBoolean(mDependancy);
		
		if(!mDependVar)
			this.setEnabled(!mDependVar);
		
	}
	
	public class ImageAdapter extends BaseAdapter {
        int mGalleryItemBackground;
        
        public ImageAdapter(Context c) {
            mContext = c;
            TypedArray a = c.obtainStyledAttributes(R.styleable.Gallery1);
            mGalleryItemBackground = a.getResourceId(
                    R.styleable.Gallery1_android_galleryItemBackground, 0);
            a.recycle();
        }

        public int getCount() {
            return mImageIds.length;
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView i = new ImageView(mContext);

            i.setImageResource(mImageIds[position]);
            i.setScaleType(ImageView.ScaleType.FIT_XY);
            i.setLayoutParams(new Gallery.LayoutParams(400, 300));
            
            // The preferred Gallery item background
            i.setBackgroundResource(mGalleryItemBackground);
            
            return i;
        }

        private Context mContext;

        private Integer[] mImageIds = {
                R.drawable.testbg1,
                R.drawable.testbg2,
                R.drawable.testbg3,
                R.drawable.testbg4,
                R.drawable.testbg5,
                R.drawable.testbg6,
                R.drawable.testbg7,
                R.drawable.testbg8,
                R.drawable.testbg9,
        };
        
  
    }

	
	  @SuppressWarnings({ "unused" })
	@Override 
	  protected View onCreateDialogView() {
		
		
	    LinearLayout.LayoutParams params;
	    layout = new LinearLayout(mContext);
	    layout.setOrientation(LinearLayout.VERTICAL);
	    layout.setPadding(15,6,15,6);
	    
        // Reference the Gallery view
        mGallery = new Gallery(mContext);
        mGallery.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        
        // Set the adapter to our custom adapter (below)
        mGallery.setAdapter(new ImageAdapter(this.mContext));
        
     // get Persistant value if exists
		SharedPreferences settings = mContext.getSharedPreferences(Service.SHARED_PREFS_NAME, Context.MODE_PRIVATE);
		mValue = settings.getInt(this.getKey(), 4);
        
        mGallery.setSelection(mValue, true);
        
        // Set a item click listener, and just Toast the clicked position
        mGallery.setOnItemClickListener(new OnItemClickListener() {
            @SuppressWarnings("rawtypes")
			public void onItemClick(AdapterView parent, View v, int position, long id) {
                Toast.makeText(mContext, "" + mBackgroundNames[position] , Toast.LENGTH_SHORT).show();
                
            }
        });
        
        // Set a Focus Change Listener to Change the Title to the 
        // Background name.
        mGallery.setOnFocusChangeListener(new OnFocusChangeListener(){

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				int viewPosition = mGallery.getPositionForView(v);
				BackgroundGalleryPreference.this.setTitle(mBackgroundNames[viewPosition] + "");
			}
        	
        });
        
        
        mGallery.getSelectedItem();
        
        // We also want to show context menu for longpressed items in the gallery
        layout.addView(mGallery);
	    
	    return layout;
	  }
	  
	  @SuppressLint("UseValueOf")
	@Override
	  protected void onDialogClosed(boolean positiveResult){
		  int value = (Integer)mGallery.getSelectedItem();
		  
		  if(positiveResult){
			  if (shouldPersist())
			      persistInt(value);
			    callChangeListener(new Integer(value));
		  }
	  }
	  
	  @Override
	  public void onDependencyChanged(Preference dependency, boolean disableDependency){
		  setEnabled(disableDependency);
		  
		  Log.d("Dependency Change", "Bool: " + disableDependency);
	  }
}
