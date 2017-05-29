package coffeestudent.drinkcoffee;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        CoffeeView coffeeView = new CoffeeView(this);
        setContentView(coffeeView);
    }
}
