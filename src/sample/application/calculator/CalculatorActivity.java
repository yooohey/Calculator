package sample.application.calculator;

import java.text.DecimalFormat;

import android.os.Bundle;
import android.renderscript.Sampler.Value;
import android.app.Activity;
import android.content.ClipboardManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CalculatorActivity extends Activity {
	//public String buffer = null;
	//public String operator = null;
	public String strTemp = null;
	public String  strResult = "0";
	int operator = 0;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_calculator, menu);
        return true;
    }
    
    public void numKeyOnClick(View v){
    	String strInKey = (String) ((Button)v).getText();
    	
    	if(strInKey.equals(".")){
    		if(this.strTemp.length() == 0){
    			this.strTemp = "0.";
    		}else{
    			if(this.strTemp.indexOf(".") == -1){
    				this.strTemp = this.strTemp + ".";
    			}
    		}
    	}else{
    		this.strTemp = this.strTemp + strInKey;
    	}
    	//TODO インスタン変数渡しちゃってる
    	this.showNumber(this.strTemp);
    	/*
    	Button button = (Button) v;
    	TextView tv=(TextView) this.findViewById(R.id.displayPanel);
    	if(tv.getText().toString().equals("0")){
    		tv.setText("");
    	}
    	tv.setText(tv.getText().toString()+button.getText().toString());
    	*/
    }
    
    private void showNumber(String strNum){
    	DecimalFormat form = new DecimalFormat("#, ##0");
    	String strDecimal = "";
    	String strInt = "";
    	String fText = "";
    	
    	if(strNum.length() > 0){
    		int decimalPoint = strNum.indexOf(".");
    		if(decimalPoint > -1){
    			strDecimal = strNum.substring(decimalPoint);
    			strInt = strNum.substring(0,decimalPoint);
    		}else{
    			strInt = strNum;
    		}
    		fText = form.format(Double.parseDouble(strInt))+strDecimal;
    	}else{
    		fText = "0";
    	}
    	
    	((TextView)findViewById(R.id.displayPanel)).setText(fText);
    }
    
    public void fuctionKeyOnClick(View v){
    	switch(v.getId()){
    	case R.id.keypadAC:
    		strTemp = "";
    		strResult = "0";
    		operator = 0;
    		break;
    	case R.id.keypadC:
    		strTemp = "";
    		break;
    	case R.id.keypadBS:
    		if(strTemp.length() == 0)return;
    		else strTemp = strTemp.substring(0,strTemp.length()-1);
    		break;
    	case R.id.keypadCopy:
    		ClipboardManager cm = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
    		cm.setText(((TextView)findViewById(R.id.displayPanel)).getText());
    		return;
    	}
    	showNumber(strTemp);
    }
    
    public void operatorKeyOnClick(View v){
    	if(operator != 0){
    		if(strTemp.length()>0){
    			strResult = doCalc();
    			showNumber(strResult);
    		}
    	}
    	else{
    		if(strTemp.length() > 0){
    			strResult = strTemp;
    		}
    	}
    	strTemp = "";
    	
    	if(v.getId() == R.id.keypadEq){
    		operator = 0;
    	}
    	else{
    		operator = v.getId();
    	}
    	
    	/*
    	Button button = (Button) v;
    	//Log.d("button=",String.valueOf(button.getId()));
    	TextView tv=(TextView) this.findViewById(R.id.displayPanel);
    	
    	if(button.getText().toString().equals("=")){
    		tv.setText(Integer.toString(doCalc(buffer,operator)));
    	}
    	else{
    		this.buffer = tv.getText().toString(); //ディスプレイの値を保存
	    	if(button.getText().toString().equals("+")){
	    		this.operator = "+";
	    		tv.setText("0");
	    	}
	    	else if(button.getText().toString().equals("-")){
	    		this.operator = "-";
	    		tv.setText("0");
	    	}
	    	else if(button.getText().toString().equals("*")){
	    		this.operator = "*";
	    		tv.setText("0");
	    	}
	    	else if(button.getText().toString().equals("/")){
	    		this.operator = "/";
	    		tv.setText("0");
	    	}
    	}
    	*/
    }
    
    public void doCalc(){
    	/*
    	int box = 0;
    	TextView tv=(TextView) this.findViewById(R.id.displayPanel);
    	
    	if(operator.equals("+")){
			box = Integer.valueOf(buffer) + Integer.valueOf(tv.getText().toString());
		}
		else if(operator.equals("-")){
			box = Integer.valueOf(buffer) - Integer.valueOf(tv.getText().toString());
		}
		else if(operator.equals("*")){
			box = Integer.valueOf(buffer) * Integer.valueOf(tv.getText().toString());
		}
		else if(operator.equals("/")){
			box = Integer.valueOf(buffer) / Integer.valueOf(tv.getText().toString());
		}
    	return box;
    	*/
	}
}

