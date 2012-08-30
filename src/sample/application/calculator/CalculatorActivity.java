package sample.application.calculator;

import java.math.BigDecimal;
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
import android.widget.Toast;

public class CalculatorActivity extends Activity {
	//public String buffer = null;
	//public String operator = null;
	public String strTemp = null;
	public String  strResult = "0";
	Integer operator = 0;
	
	
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
    	showNumber(this.strTemp);
    }
    
    public void operatorKeyOnClick(View v){
    	if(this.operator != 0){
    		if(this.strTemp.length()>0){
    			this.strResult = this.doCalc();
    			this.showNumber(this.strResult);
    		}
    	}
    	else{
    		if(this.strTemp.length() > 0){
    			this.strResult = this.strTemp;
    		}
    	}
    	this.strTemp = "";
    	
    	if(v.getId() == R.id.keypadEq){
    		this.operator = 0;
    	}
    	else{
    		this.operator = v.getId();
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
    
    private String doCalc(){
    	BigDecimal bd1 = new BigDecimal(strResult);
    	BigDecimal bd2 = new BigDecimal(strTemp);
    	BigDecimal result = BigDecimal.ZERO;
    	
    	switch(operator){
    	case R.id.keypadAdd:
    		result = bd1.add(bd2);
    		break;
    	case R.id.keypadSub:
    		result = bd1.subtract(bd2);
    		break;
    	case R.id.keypadMulti:
    		result = bd1.multiply(bd2);
    		break;
    	case R.id.keypadDiv:
    		if(!bd2.equals(BigDecimal.ZERO)){
    			result = bd1.divide(bd2, 12, 3);
    		}else{
    			Toast toast = Toast.makeText(this, R.string.toast, 10000);
    			toast.show();
    		}
    		break;
    	}
    	if(result.toString().indexOf(".") >= 0){
    		return result.toString().replaceAll("¥¥.0 + $|0+$", "");
    	}else{
    		return result.toString();
    	}
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

