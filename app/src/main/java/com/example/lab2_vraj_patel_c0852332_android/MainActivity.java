package com.example.lab2_vraj_patel_c0852332_android;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView product_list;
    TextView product_list_count;
    Database_Helper database_Helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database_Helper = new Database_Helper(MainActivity.this);
        Button delete=findViewById(R.id.delete_product);
        Button insert=findViewById(R.id.insert_product);
        Button update=findViewById(R.id.update_product);
        Button read=findViewById(R.id.refresh_data);
        product_list =findViewById(R.id.all_product_list);
        product_list_count =findViewById(R.id.product_list_count);


        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh_Product();

            }
        });

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Show_Input();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_UpdateId();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_Delete();
            }
        });


    }

    private void Show_Input() {
        AlertDialog.Builder al=new AlertDialog.Builder(MainActivity.this);
        View view=getLayoutInflater().inflate(R.layout.insert,null);
        final EditText productID=view.findViewById(R.id.productid);
        final EditText productName=view.findViewById(R.id.productname);
        final EditText productDescription=view.findViewById(R.id.productdescription);
        final EditText productPrice=view.findViewById(R.id.price);
        Button insertBtn=view.findViewById(R.id.insert_btn);
        al.setView(view);

        final AlertDialog alertDialog=al.show();

        insertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Database_Model productModel =new Database_Model();
                productModel.setproduct_id(productID.getText().toString());
                productModel.setProduct_name(productName.getText().toString());
                productModel.setProduct_description(productDescription.getText().toString());
                productModel.setProduct_price(productPrice.getText().toString());
                Date date=new Date();
                productModel.setCreated_at(""+date.getTime());
                database_Helper.AddProduct(productModel);
                alertDialog.dismiss();
                refresh_Product();
            }
        });
    }



    private void show_Delete() {
        AlertDialog.Builder al=new AlertDialog.Builder(MainActivity.this);
        View view=getLayoutInflater().inflate(R.layout.delete,null);
        al.setView(view);
        final EditText id_input=view.findViewById(R.id.id_input);
        Button delete_btn=view.findViewById(R.id.delete_btn);
        final AlertDialog alertDialog=al.show();

        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database_Helper.delete_Product(id_input.getText().toString());
                alertDialog.dismiss();
                refresh_Product();

            }
        });


    }

    private void show_UpdateId() {
        AlertDialog.Builder al=new AlertDialog.Builder(MainActivity.this);
        View view=getLayoutInflater().inflate(R.layout.updateid,null);
        al.setView(view);
        final EditText id_input=view.findViewById(R.id.id_input);
        Button fetch_btn=view.findViewById(R.id.update_id_btn);
        final AlertDialog alertDialog=al.show();
        fetch_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_Product(id_input.getText().toString());
                alertDialog.dismiss();
                refresh_Product();
            }
        });

    }

    private void show_Product(final String id) {
        Database_Model productModel = database_Helper.to_Get_Product(Integer.parseInt(id));
        AlertDialog.Builder al=new AlertDialog.Builder(MainActivity.this);
        View view=getLayoutInflater().inflate(R.layout.update,null);
        final EditText productID=view.findViewById(R.id.productid);
        final EditText productName=view.findViewById(R.id.productname);
        final EditText productDescription=view.findViewById(R.id.productdescription);
        final EditText productPrice=view.findViewById(R.id.price);
        Button update_btn=view.findViewById(R.id.update_btn);
        al.setView(view);

        productID.setText(productModel.getproduct_id());
        productName.setText(productModel.getProduct_name());
        productDescription.setText(productModel.getProduct_description());
        productPrice.setText(productModel.getProduct_price());

        final AlertDialog alertDialog=al.show();
        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Database_Model productModel =new Database_Model();
                productModel.setproduct_id(productID.getText().toString());
                productModel.setId(id);
                productModel.setProduct_name(productName.getText().toString());
                productModel.setProduct_description(productDescription.getText().toString());
                productModel.setProduct_price(productPrice.getText().toString());
                database_Helper.update_Product(productModel);
                alertDialog.dismiss();
                refresh_Product();
            }
        });
    }

    private void refresh_Product() {
        product_list_count.setText("PRODUCT COUNT : "+database_Helper.to_Get_TotalCount());

        List<Database_Model> productModelList = database_Helper.to_GetAll_Products();
        product_list.setText("");
        for(Database_Model productModel : productModelList){
            product_list.append("ID :- "+ productModel.getId()+" | Product ID :- "+ productModel.getproduct_id()+" | Product Name :- "+ productModel.getProduct_name()+" | Product Price :- "+ productModel.getProduct_price()+ " | Product description :- "+ productModel.getProduct_description()+" \n\n");
        }
    }

}