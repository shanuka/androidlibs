package quick.action;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class QuickActionSampleAppActivity extends Activity
{
    
ListView resultPane;
List<FriendInfo> list;
CustomAdapter adapter;


/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        resultPane = (ListView)findViewById(R.id.list);
        String friendList[] = {"Mobile no.1 vijayakumar","Mobile no.2","Mobile no.3","Mobile no.4","Mobile no.5","Mobile no.6","Mobile no.7","Mobile no.8","Mobile no.9","Mobile no.10"};
        Drawable image = null;
        
        list = new ArrayList<FriendInfo>();
        for(int i=0;i<friendList.length;i++)
        {
         list.add(new FriendInfo(friendList[i],image));
        }
                
        adapter = new CustomAdapter(this,list);
        resultPane.setAdapter(adapter);
               
        
     final QuickActionIcons edit = new QuickActionIcons();;
    
       edit.setTitle("Edit");  
        edit.setIcon(getResources().getDrawable(R.drawable.edit));
        edit.setOnClickListener(new OnClickListener()
        {
         public void onClick(View v)
         {
         Toast.makeText(QuickActionSampleAppActivity.this,"Edit Contact",Toast.LENGTH_SHORT).show();
         }
    

        });
        
               
        final QuickActionIcons call = new QuickActionIcons();;
        
        call.setTitle("Call");       
        call.setIcon(getResources().getDrawable(R.drawable.call));
        call.setOnClickListener(new OnClickListener()
        {
         public void onClick(View v)
         {
         Toast.makeText(QuickActionSampleAppActivity.this,"Call Contact",Toast.LENGTH_SHORT).show();
         }
    

        });
  final QuickActionIcons call3 = new QuickActionIcons();;
        
        call3.setTitle("Call3");      
        call3.setIcon(getResources().getDrawable(R.drawable.call));
        call3.setOnClickListener(new OnClickListener()
        {
         public void onClick(View v)
         {
        // Toast.makeText(QuickActionSampleAppActivity.this,"Call Contact",Toast.LENGTH_SHORT).show();
         }
    

        });
  final QuickActionIcons call1 = new QuickActionIcons();;
        
        call1.setTitle("Call 1");      
        call1.setIcon(getResources().getDrawable(R.drawable.call));
        call1.setOnClickListener(new OnClickListener()
        {
         public void onClick(View v)
         {
        // Toast.makeText(QuickActionSampleAppActivity.this,"Call Contact",Toast.LENGTH_SHORT).show();
         }
    

        });
  final QuickActionIcons call2 = new QuickActionIcons();;
        
        call2.setTitle("Call 2");       
        call2.setIcon(getResources().getDrawable(R.drawable.call));
        call2.setOnClickListener(new OnClickListener()
        {
         public void onClick(View v)
         {
        // Toast.makeText(QuickActionSampleAppActivity.this,"Call Contact",Toast.LENGTH_SHORT).show();
         }
    

        });
        
        final QuickActionIcons send_data = new QuickActionIcons();;
        
        send_data.setTitle("Send Data");      
        send_data.setIcon(getResources().getDrawable(R.drawable.bluetooth));
        send_data.setOnClickListener(new OnClickListener()
        {
         public void onClick(View v)
         {
       //  Toast.makeText(QuickActionSampleAppActivity.this,"Start Transfer of Data",Toast.LENGTH_SHORT).show();
         }
    

        });
        
    
    
    resultPane.setOnItemClickListener(new OnItemClickListener()
    {
public void onItemClick(AdapterView<?> parent, View view, int position, long id)
{
QuickActionBar qab = new QuickActionBar(view);

qab.addItem(edit);
qab.addItem(call);
qab.addItem(send_data);
qab.addItem(call1);
qab.addItem(call2);
qab.addItem(call3);
qab.setAnimationStyle(QuickActionBar.GROW_FROM_LEFT);

qab.show();
}
    });
 }

}


