package com.Chitra;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.List;
/* this program reads the data from a file which has info in a list of list( that how I read it) *
It basically contains a list of beverages sold in a coffee shop with the expenses and revenue for each drink.
We have to take the number of each drink sold by the user to be able to calculate the total expense, revenue and the profit for each drink and finally, over all expense, revenue and the profit for the shop*/

public class Main
{

    public static void main(String[] args) throws IOException
    {
        // a reader object created to read from a file called coffee.txt which resides in the same directory as project.
        FileReader reader = new FileReader("coffee.txt");
        BufferedReader bufReader = new BufferedReader(reader);
        String line = bufReader.readLine();
        List<String> CoffeeInfo = new ArrayList<>();
        // a hashmap created to get the coffedata with drink’s name as key and values in a list
        HashMap<String, List<Double>> CoffeeData = new HashMap<String, List<Double>>();
        // a hashmap created to give the coffedata with drink's ShopCoast and CustomerCost to the User-defined method called Calculation2
        HashMap<String, List<Double>> CoffeeData1 = new HashMap<String, List<Double>>();

        while (line != null)
        {
            CoffeeInfo.add(line);  // adds line to the list
            System.out.println(line);
            line = bufReader.readLine();

        }
        // a variable called key to save key from the hashmap
        String kEy = " ";
        // for each loop to split the items from the lines in the file
        for (String ST : CoffeeInfo
                ) {
            List<Double> valUes = new ArrayList<>();
            kEy = ST.split(";")[0];
            valUes.add(Double.parseDouble(ST.split(";")[1]));
            valUes.add(Double.parseDouble(ST.split(";")[2]));

            CoffeeData1.put(kEy, valUes);
        }

        int size = CoffeeInfo.size();
        HashMap<String, Double> CoffeeNameInputData = new HashMap<>();
        Double[] NumberofBeverages = new Double[size]; // an array to store the number of each drink from the user
        FileWriter writer = new FileWriter("Sales-Report.txt"); // an object writer created to write into a text file called sales-report
        // a loop to collect the number of from user and create a hashmap with name and number of drink sold every day
        for (int i = 0; i < size; i++)
        {
            String[] BeveragesName = CoffeeData1.keySet().toArray((new String[CoffeeData1.size()]));//new String[]{"cappuccino", "espresso", "latte", "black tea", "herbal tea", "macchiato", "americano", "cold press", "hot chocolate", "coffee", "chai tea", "mocha"};

            NumberofBeverages[i] = GetUserInput(BeveragesName[i]);
            CoffeeNameInputData.put(BeveragesName[i], NumberofBeverages[i]);
        }
        CoffeeData = Calculation2(CoffeeNameInputData,CoffeeData1);
        Double[] FinalTotal = new Double[]{0.0, 0.0, 0.0};  // an array initialized with 0th item for each revenue, profit and expense
        // a for each loop to convert the list from file into an array
        for (String key : CoffeeData.keySet())
        {
            Double[] array = (CoffeeData.get(key).toArray(new Double[CoffeeData.get(key).size()])); // stack-flow-to convert a list into an array.
            for (int j = 0; j < 3; j++) // this finds the total profit, expense and revenue for all the drinks.
            {
                FinalTotal[j] = FinalTotal[j] + array[j + 1];
            }
            //System.out.println(key + ": Sold " + Math.round(array[0]) + ", Expenses $" + Math.round(array[1]) + ", Revenue $" + Math.round(array[2]) + ", Profit $" + Math.round(array[3])+"\n");
            // Writer object writes into the file.
            writer.write(key + ": Sold " + Math.round(array[0]) + ", Expenses $" + Math.round(array[1]) + ", Revenue $" + Math.round(array[2]) + ", Profit $" + Math.round(array[3]) + "\n");
        }
        //System.out.println("\nTotal Expenses $" + Math.round(FinalTotal[0]) + "\nTotal Revenue $" + Math.round(FinalTotal[1]) + "\nTotal Profit $" + Math.round(FinalTotal[2]));
        writer.write("\nTotal Expenses $" + Math.round(FinalTotal[0]) + "\nTotal Revenue $" + Math.round(FinalTotal[1]) + "\nTotal Profit $" + Math.round(FinalTotal[2]));
        writer.close();
    }
    // a user defined method to take user and validate it for any input mismatch
    public static Double GetUserInput(String name)
    {
        Double number =0.0;
        while (true)
        {
            try
            {
                Scanner scanner = new Scanner(System.in);
                System.out.println("How many " + name + " Drink were sold today ?");
                number = scanner.nextDouble();
                break;

            }
            catch (InputMismatchException ime)
            {
                System.out.println("Please enter a double number");
                continue;
            }

        }
        return number;
    }
    // a user defined method which has two hashmaps as argument, first hashmap gives name and number sold; second hashmap gives all the data for calculation
    public static HashMap<String, List<Double>> Calculation2(HashMap<String, Double> Calc, HashMap<String, List<Double>> RateCard)
    {
        // some variables defined to store data after extracting it from hashmaps sent by the method called.
        String name = "";
        Double NumberofBeverages = 0.0;
        Double Expenses = 0.0;
        Double Revenue = 0.0;
        Double Profit = 0.0;
        // a tem hashmap to store info for the calculation done in this method
        HashMap<String, List<Double>> SalesReport = new HashMap<String, List<Double>>();
        // for each loop to get the name, number of drinks sold, ShopExpense and CustomerCost
        for (String key : Calc.keySet()
                ) {
            name = key;
            NumberofBeverages = Calc.get(key);
            Double ShopExpense = RateCard.get(key).get(0);
            Double CustomerExpense = RateCard.get(key).get(1);
            // an array list to add all the information after calculation
            List<Double> Data = new ArrayList<>();
            // Calculations- required to get the results
            Expenses = NumberofBeverages * ShopExpense;
            Revenue = NumberofBeverages * CustomerExpense;
            Profit = Revenue - Expenses;
            Data.add(NumberofBeverages);
            Data.add(Expenses);
            Data.add(Revenue);
            Data.add(Profit);
            SalesReport.put(name, Data); // adding data into hashmap

        }
        return SalesReport; // returning a hashmap with all the info.
    }
}














