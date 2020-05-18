package com.example.soni;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends BaseAdapter {

    Context c;
    List<PDFDoc> pdfDocs;
    LayoutInflater inflater;
    ArrayList<String> year = new ArrayList<>(50);
    ArrayList<String> PDFtype = new ArrayList<>(50);

    public String getYear(int i) {
        return year.get(i);
        }

    public String getPDFtype(int i){
        return PDFtype.get(i);
    }

    public MyAdapter(Context c, List<PDFDoc> pdfDocs) {
        this.c = c;
        this.pdfDocs = pdfDocs;
    }

    @Override
    public int getCount() {
        return pdfDocs.size();
    }

    @Override
    public Object getItem(int i) {
        return pdfDocs.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }




    @Override
    public View getView(int i, View view, final ViewGroup viewGroup) {

        if(inflater ==null)
        {
            inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(view==null)
        {
            //INFLATE CUSTOM LAYOUT
            view= inflater.inflate(R.layout.pdfviewmodel,viewGroup,false);
        }

        final PDFDoc pdfDoc= (PDFDoc) this.getItem(i);

        TextView nameTxt= (TextView) view.findViewById(R.id.nameTxt);
        ImageView img= (ImageView) view.findViewById(R.id.pdfImage);
        final Spinner spinner = (Spinner) view.findViewById(R.id.selectyear);
        //BIND DATA


        nameTxt.setText(pdfDoc.getName());
        img.setImageResource(R.drawable.pdf_icon);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(viewGroup.getContext(),R.array.SelectYear,android.R.layout.simple_list_item_1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
     //   spinner.setOnItemSelectedListener(this);

        final int z=i;
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(viewGroup.getContext(),String.valueOf(z),Toast.LENGTH_SHORT).show();
             //   year.set(position,  parent.getItemAtPosition(position).toString());
                try{    //parent.getItemAtPosition(position).toString()
                        year.add(z,parent.getItemAtPosition(position).toString());
                        year.set(z,parent.getItemAtPosition(position).toString());
                                       //.get(z).replace(getYear(z),parent.getItemAtPosition(z).toString());

                    //Toast.makeText(viewGroup.getContext(),parent.getItemAtPosition(position) +" "+ String.valueOf(position),Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(viewGroup.getContext(),"Exception year",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final Spinner spinnerpdftype = (Spinner) view.findViewById(R.id.pdftype);
        final ArrayAdapter<CharSequence> pdftypeadapter = ArrayAdapter.createFromResource(viewGroup.getContext(),R.array.spinner_pdftype,android.R.layout.simple_list_item_1);
        pdftypeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerpdftype.setAdapter(pdftypeadapter);

        spinnerpdftype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 try{
                  //   Toast.makeText(viewGroup.getContext(),String.valueOf(z),Toast.LENGTH_SHORT).show();
                PDFtype.add(z,parent.getItemAtPosition(position).toString());
                PDFtype.set(z,parent.getItemAtPosition(position).toString());
                     /*     if(PDFtype.get(z)..isEmpty())
                     {
                     PDFtype.add(z,parent.getItemAtPosition(position).toString());
                     Toast.makeText(viewGroup.getContext(),"pdf was empty",Toast.LENGTH_SHORT).show();

                     }
                     else
                     {
                         PDFtype.remove(z);//.get(z).replace(getPDFtype(z),parent.getItemAtPosition(position).toString());
                          PDFtype.add(z,parent.getItemAtPosition(position).toString());
                     }
                */     //Toast.makeText(viewGroup.getContext(),String.valueOf(parent(view)) ,Toast.LENGTH_SHORT).show();
                 }catch (Exception e){
                    Toast.makeText(viewGroup.getContext(),"Exceptionpdftype",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //VIEW ITEM CLICK
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPDFView(pdfDoc.getPath());
            }
        });
        return view;
    }

    //OPEN PDF VIEW
    private void openPDFView(String path)
    {
        Intent i=new Intent(c,PDF_view.class);
        i.putExtra("PATH",path);
        c.startActivity(i);
    }
/*    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
      //  Toast.makeText(parent.getContext(),parent.getItemAtPosition(position).toString(),Toast.LENGTH_SHORT).show();

        try{    //parent.getItemAtPosition(position).toString()
            //Toast.makeText(viewGroup.getContext(),"Done",Toast.LENGTH_SHORT).show();
                    year.set(position,parent.getItemAtPosition(position).toString() );

        }catch (Exception e){
            Toast.makeText(parent.getContext(),"Exception",Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }*/

}
