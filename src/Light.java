import com.pi4j.io.gpio.*;

import java.lang.reflect.Array;
import java.util.*;

public class Light
{
    //values
    // create gpio controller
    final GpioController gpio = GpioFactory.getInstance();

    // provision gpio pin #01 and #02 as an output pin and turn on
    final GpioPinDigitalOutput light1 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "MyLED", PinState.HIGH);
    final GpioPinDigitalOutput light2 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "MyLED", PinState.HIGH);
    GpioPinDigitalOutput[] lights = {light1,light2};

    //in parents class have them entering true for turn on light set the value of the index to 1
    public void onoff(int Lightnum, int[] Lightstate, int[] Lightlens)
    {
        //1 Light
        if(Lightnum != -1)
        {
            //State of light
            if(Lightstate[Lightnum] == 1)
           {
               //If there is a set length
               if(Lightlens[Lightnum] != -1)
               {
                   lights[Lightnum].pulse(((long)Lightlens[Lightnum]));
               }
               else
               {
                   lights[Lightnum].high();
               }
           }
           else
           {
               lights[Lightnum].low();
           }

        }
        //Mutiple Lights
        else
        {
            for (int x = 0; x < lights.length; x++)
            {
                //State of Light
                if (Lightstate[x] == 1)
                {
                    //If there is a set length
                    if (Lightlens[x] != -1)
                    {
                        lights[x].pulse(((long) Lightlens[x]));
                    } else
                    {
                        lights[x].high();
                    }
                } else
                {
                    lights[x].low();
                }
            }
        }


    }
    public void strobe(int[] values)
    {

    }
}
