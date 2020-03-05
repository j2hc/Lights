import com.pi4j.io.gpio.*;
import java.util.*;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.GpioController;
public class lightsMain
{
    static int temp;
    static final GpioController gpio = GpioFactory.getInstance();
    static final GpioPinDigitalOutput light1 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "MyLED", PinState.HIGH);
    static final GpioPinDigitalOutput light2 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "MyLED", PinState.HIGH);
    static final GpioPinDigitalOutput[] Lights = {light1, light2};
    static final Scanner engine = new Scanner(System.in);
    static int Lightnum;
    static int[] Lightstate = {-1, -1};
    static int[] Lightlens = {-1, -1};
    static Light Light;

    public static void main(String[] args)
    {
        while (true)
        {

            System.out.println("What do you want do today? \n To Strobe press: 1\n To turn On or Off press: 2\n To set a timer press: 3?");
            if (engine.hasNextInt())
            {
                temp = engine.nextInt();
                if(temp ==1 || temp == 2 || temp ==3)
                {
                    switch (temp)
                    {
                        case 1:
                            break;
                        case 2:
                            toggle();
                            break;
                        case 3:
                            break;
                    }
                }
                else
                {
                    System.out.println("Invalid Input");
                }
            }
            else
            {
                System.out.println("Invalid Input");
            }
        }
    }
    //This method sets up which lights you are switching and calls length and State to build those arrays in preperation to call Light.onoff
    public static void toggle()
    {
        temp = InputCheckRestricted("Would you like to adjust 1 or 2 lights?\n Input 1 or 2");
        if (temp == 1)
        {
            temp = InputCheckRestricted("Which Light would you like to adjust? 1 or 2");
            {
                Lightnum = temp - 1;
            }
        } else
        {
            Lightnum = -1;
        }
        State();

        InputCheckRestricted("Would like to set a duration for the light/lights?\n 1 for yes");
        if(temp ==1)
        {

            length();
        }
        Light.onoff(Lightnum,Lightstate,Lightlens);
    }
    //This method builds the Lightlens array
    public static void length()
    {
        int[] val={1,2};
       temp=InputCheckRestricted("Would like to set durations of the light's independently? \n1 for yes\n 2 for no");
       if (temp == 1)
       {
           for (int x = 0; x < Lights.length; x++)
           {
              Lightlens[x] =InputCheck("Input Length");
           }
       } else
           {
               Lightlens[0] = InputCheck("Input length");
           }
    }
    //This method builds the LightState array
    public static void State()
    {
        if(Lightnum ==-1)
        {
            for(int x =0;x<Lights.length;x++)
            {
                Lightstate[x] =InputCheckRestricted("Please input a state for light" + (x + 1) + "\n1 for on\n 2 for off");
            }
        }
        else
        {
            Lightstate[Lightnum] = InputCheckRestricted("Please input a state\n1 for on\n 2 for off");
        }
    }
    //Input check and Restricted Input check both take a question to print then check the validity of the given user input. Restricted checks
    //if the input is an int and if the int is equal to 1 or 2, Unrestricted just checks for if it is an int
    public static int InputCheckRestricted(String question)
    {
        boolean check = true;
        while(check)
        {
            System.out.println(question);
            if (engine.hasNextInt())
            {
                temp = engine.nextInt();
                if ((temp == 1 || temp == 2))
                {
                   check = false;
                }
                else
                {
                    System.out.println("Invalid Input, out of range int");
                }
            }
            else
            {
                System.out.println("Invalid Input, not an int");
            }
        }
        return temp;

    }
    public static int InputCheck(String question)
    {
        boolean check = true;
        while(check)
        {
            System.out.println(question);
            if (engine.hasNextInt())
            {
                temp = engine.nextInt();
                return temp;
            }
            else
            {
                System.out.println("Invalid Input, not an int");
            }
        }
        return temp;
    }
}
