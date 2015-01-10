package com.example.hayder.dijkstras01;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

class TraceOfNode {

    private int Dest;
    private int PrevNode;
    private int Iter;
    public TraceOfNode( int dest, int prevNode, int iter) {
        Dest = dest;
        PrevNode = prevNode;
        Iter = iter;
    }
    public int getDest()
    {
        return Dest;
    }

    public int getPrevNode()
    {
        return PrevNode;
    }

    public int getIter()
    {
        return Iter;
    }
}
public class MainActivity extends ActionBarActivity {
    Button button1;
    Spinner spin1,spin2;
    TextView tv;

    static Integer n;
    static int [][] G;
    static TraceOfNode[][] StageofTrace;


    static int source;
    static int Gool; //destination
    static int DISTANCES;

    static int [] Vis;
    static int [] UnVis;
    static int [] ShortPath;
    static int count =0;
    static String[] items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //first
        button1 = (Button) findViewById(R.id.button1);
//LINKS WITH TE XML ELEMENTS
        tv = (TextView) findViewById(R.id.textView2);
//THESE ARE THE VARIOUS "SPOTS", OBVIOUSLY NOT ALL ARE MENTIONED
        items = new String[] {"A", "B",
                "C", "D", "E", "F", "G"};

        final Spinner spin1 = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin1.setAdapter(adapter);
        spin1.setSelection(0);

        //second step


        final Spinner spin2 = (Spinner) findViewById(R.id.spinner4);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, items);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin2.setAdapter(adapter2);
        spin2.setSelection(1);

        //third

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count =0;
                int a=spin1.getSelectedItemPosition();
                int b=spin2.getSelectedItemPosition();

                n=7;
                G = new int[n+1][n+1];
                StageofTrace = new TraceOfNode[n+1][n+1];
                ShortPath = new int [n+1];


                for(int x=1;x<=n;x++)
                    for(int y=1;y<=n;y++)
                    {
                        if(x != y)
                            G[x][y]= 5000;//+ve infinity
                        else
                            G[x][y] = 0;
                    }
                //THESE ARE THE DISTANCES BETWEEN 2 NODES OF  THE GRAPH, IF A DIRECT PATH EXISTS BETWEEN THEM
                //VALUES WHICH CAN ALSO BE CHANGED AS PER NEEDS
                G[1][2]=16;
                G[1][3]=9;
                G[1][4]=35;

                G[2][4]=12;
                G[2][5]=25;

                G[3][4]=15;
                G[3][6]=22;

                G[6][7]=14;
                G[5][7]=8;


                G[4][7]=19;
                G[4][6]=17;
                G[4][5]=14;



                source=a+1;
                Gool = b+1;

                Vis =new int [n+1];
                UnVis =new int [n+1];


                for(int x=1;x<=n;++x)
                {
                    UnVis[x] = x;
                    Vis[x] = 0;
                    if(x==source)
                        StageofTrace[0][x]= new TraceOfNode(0,0,0);
                    else
                        StageofTrace[0][x]= new TraceOfNode(5000,0,0);

                }

                //Algorithm  Compute
                for (int x=1;x<=n;++x)
                {
                    DISTANCES=5000;
                    int Current =0;
                    for(int k=1;k<=n;++k)
                    {
                        int tmpdes = StageofTrace[x-1][k].getDest();
                        if(DISTANCES>tmpdes)
                        {
                            if(Vis[k]==0)
                            {
                                DISTANCES = tmpdes;
                                Current = k;
                            }
                        }
                    }

                    UnVis[Current] = 0;
                    Vis[Current]=Current;
                    if(Current == Gool)
                    {
                        GetShortPath(x-1,Current);
                        x=n+1;
                    }
                    else
                    {
                        for(int y=1;y<=n;++y)
                        {

                            if(Vis[y] == 0 )
                            {
                                int DIS1 =0;
                                DIS1 = Math.min( G[Current][y],G[y][Current]);

                                DIS1 =  DISTANCES + DIS1;

                                int DIS2 = StageofTrace[x-1][y].getDest();

                                if(DIS1 < DIS2)
                                {
                                    StageofTrace[x][y] = new TraceOfNode(DIS1,Current,x);
                                }
                                else
                                {

                                    StageofTrace[x][y] = new TraceOfNode(StageofTrace[x-1][y].getDest(),StageofTrace[x-1][y].getPrevNode(),StageofTrace[x-1][y].getIter());
                                }
                            }
                            else
                            {
                                StageofTrace[x][y] = new TraceOfNode(StageofTrace[x-1][y].getDest(),StageofTrace[x-1][y].getPrevNode(),StageofTrace[x-1][y].getIter());

                            }




                        }
                    }



                }


                String outPath="Your path should be:\n" + items[ShortPath[count]-1];
                for(int i=count-1;i>=0 ;i--)
                {
                    outPath+=" -> "+items[ShortPath[i]-1];
                }


                //fourth
                TextView tv2 = (TextView) findViewById(R.id.textView3);
                tv2.setText(outPath);

            }
        });

    }



    void GetShortPath(int iter , int LastCurrent)
    {
        if((LastCurrent!= source) && (count <=(n*2)))
        {
            ShortPath[count] = LastCurrent;
            count +=1;
            GetShortPath(StageofTrace[iter][LastCurrent].getIter(),StageofTrace[iter][LastCurrent].getPrevNode());
        }
        else if(LastCurrent== source)
        {
            ShortPath[count] = LastCurrent;

        }
        else if(count >(n*2))
        {


            String tsttxt = "Out of Range";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(MainActivity.this ,tsttxt, duration);
            toast.show();

        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
