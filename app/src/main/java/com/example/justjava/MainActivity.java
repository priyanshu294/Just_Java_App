package com.example.justjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;


/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if(quantity == 100){

            Toast.makeText(this, "You cannot have more than 100 coffees", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        display(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if(quantity == 1) {

            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        display(quantity);
    }

    private String createOrdersummary(String name,int price,boolean addwhippedcream,boolean addchocolate){

        String masge = "Name: " + name;
        masge = masge + "\nAdd whipped cream? " + addwhippedcream;
        masge = masge + "\nAdd Chocolate? " + addchocolate;
        masge = masge + "\nQuantity: " + quantity;
         masge = masge +  "\nTotal: $" + price;
        masge = masge + "\nThank You!";
        return  masge;
    }


    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
       // displayPrice(quantity* 5);

        EditText text = (EditText)findViewById(R.id.name_field);
        String name = text.getText().toString();


        CheckBox whippedcream = (CheckBox) findViewById(R.id.checkbox_meat);
        boolean haswhipped = whippedcream.isChecked();

        CheckBox chocolate = (CheckBox) findViewById(R.id.checkbox_cho);
        boolean haschocolate = chocolate.isChecked();

        int price = calculateprice(haswhipped,haschocolate);
       String masge = createOrdersummary(name,price,haswhipped,haschocolate);

            // Intent sending
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT,masge);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }


    }

    // calculate price method
    private  int calculateprice(boolean addwhippedcream,boolean addchocolate){
        //price of 1 cup of coffee
        int baseprice = 5;

        if(addwhippedcream){
            baseprice = baseprice + 1;
        }
        if(addchocolate){
            baseprice = baseprice + 2;
        }
        return quantity * baseprice;
    }


    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given price on the screen.
     */
//    private void displayPrice(int number) {
//        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
//        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
//    }


}
