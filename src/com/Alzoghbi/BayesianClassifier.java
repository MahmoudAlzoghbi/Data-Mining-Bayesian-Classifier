package com.Alzoghbi;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class BayesianClassifier {

	public static void main(String[] args) throws Exception, IOException {
		int size_of_data = 1728;
		double sizeOf_training_data = size_of_data * 0.75;
		double sizeOf_testing_data = size_of_data - sizeOf_training_data;
		List<String> unExpected = new ArrayList<>();
		// Attribute Description
		List<String> BuyingPrice = new ArrayList<>();
		List<Integer> ValueOfBuying = new ArrayList<>();
		List<String> UniqueOfBuying = new ArrayList<>();
		UniqueOfBuying.add("low");
		UniqueOfBuying.add("med");
		UniqueOfBuying.add("high");
		UniqueOfBuying.add("vhigh");
		//double[][] ProbOfBaying = new double[4][4];
		List<String> MaintenancePrice = new ArrayList<>();
		List<Integer> ValueOfMaintenance = new ArrayList<>();
		List<String> UniqueOfMaintenance = new ArrayList<>();
		UniqueOfMaintenance.add("low");
		UniqueOfMaintenance.add("med");
		UniqueOfMaintenance.add("high");
		UniqueOfMaintenance.add("vhigh");
		//double[][] ProbOfMaintenance = new double[4][4];
		List<String> NumberOfDoors = new ArrayList<>();
		List<Integer> ValueOfDoors = new ArrayList<>();
		List<String> UniqueNumOfDoor = new ArrayList<>();
		UniqueNumOfDoor.add("2");
		UniqueNumOfDoor.add("3");
		UniqueNumOfDoor.add("4");
		UniqueNumOfDoor.add("5more");
		//double[][] ProbOfDoor = new double[4][4];
		List<String> CapacityInTermsOfPersonsToCarry = new ArrayList<>();
		List<Integer> ValueOfCapacity = new ArrayList<>();
		List<String> UniqueCapacityOfCarry = new ArrayList<>();
		UniqueCapacityOfCarry.add("2");
		UniqueCapacityOfCarry.add("4");
		UniqueCapacityOfCarry.add("more");
		//double[][] ProbOfCarry = new double[3][4];
		List<String> SizeOfLuggageBoot = new ArrayList<>();
		List<Integer> ValueOfLuggage = new ArrayList<>();
		List<String> UniqueSizeOfLuggage = new ArrayList<>();
		UniqueSizeOfLuggage.add("small");
		UniqueSizeOfLuggage.add("med");
		UniqueSizeOfLuggage.add("big");
		//double[][] ProbOfLuggage = new double[3][4];
		List<String> EstimatedSafetyOfTheCar = new ArrayList<>();
		List<Integer> ValueOfEstimation = new ArrayList<>();
		List<String> UniqueEstimatedSafety = new ArrayList<>();
		UniqueEstimatedSafety.add("low");
		UniqueEstimatedSafety.add("med");
		UniqueEstimatedSafety.add("high");
		//double[][] ProbOfSafety = new double[3][4];
		List<String> CarAcceptability = new ArrayList<>();
		List<Integer> ValueOfCar = new ArrayList<>();
		List<String> ValuesRange = new ArrayList<>();
		List<Integer> NumOFValuesRange = new ArrayList<>();
		List<Double> Probability = new ArrayList<>();
		double[][] P_F1 = new double[(int) sizeOf_testing_data][4];
		double[][] P_F2 = new double[(int) sizeOf_testing_data][4];
		double[][] P_F3 = new double[(int) sizeOf_testing_data][4];
		double[][] P_F4 = new double[(int) sizeOf_testing_data][4];
		double[][] P_F5 = new double[(int) sizeOf_testing_data][4];
		double[][] P_F6 = new double[(int) sizeOf_testing_data][4];
		//
		File f = new File("CarData.xls");
		Workbook wb = Workbook.getWorkbook(f);
		Sheet s = wb.getSheet(0);
		int row = s.getRows();
		int col = s.getColumns();
		for (int i = 0; i < row; i++) {
			Cell c = s.getCell(0, i);
			BuyingPrice.add(c.getContents().toString());
			c = s.getCell(1, i);
			MaintenancePrice.add(c.getContents().toString());
			c = s.getCell(2, i);
			NumberOfDoors.add(c.getContents().toString());
			c = s.getCell(3, i);
			CapacityInTermsOfPersonsToCarry.add(c.getContents().toString());
			c = s.getCell(4, i);
			SizeOfLuggageBoot.add(c.getContents().toString());
			c = s.getCell(5, i);
			EstimatedSafetyOfTheCar.add(c.getContents().toString());
			c = s.getCell(6, i);
			CarAcceptability.add(c.getContents().toString());
		}
		String help = "";
		for (int i = 0; i < CarAcceptability.size(); i++) {
			help = CarAcceptability.get(i);
			if (ValuesRange.isEmpty()) {
				ValuesRange.add(help);
			} else if (!ValuesRange.contains(help))
				ValuesRange.add(help);

		}
		int counter = 0;
		double prob;
		for (int i = 0; i < ValuesRange.size(); i++) {
			for (int j = 0; j < sizeOf_training_data; j++) {
				if (ValuesRange.get(i) == CarAcceptability.get(j))
					counter++;
			}
			prob = counter / sizeOf_training_data;
			Probability.add(prob);
			NumOFValuesRange.add(counter);
			counter = 0;
		}
		// Compute To Testing Data The Expected Class For Buying Price
		counter = 0;
		for (int i = (int) sizeOf_training_data, c = 0; i < size_of_data; i++, c++) {
			for (int k = 0; k < ValuesRange.size(); k++) {
				for (int j = 0; j < sizeOf_training_data; j++) {
					if ((BuyingPrice.get(i).equals(BuyingPrice.get(j)))
							&& (CarAcceptability.get(j).equals(ValuesRange.get(k))))
						counter++;
				}
				P_F1[c][k] = (double) counter / NumOFValuesRange.get(k);
				counter = 0;
			}
		}
		// Compute To Testing Data The Expected Class For Maintenance
		counter = 0;
		for (int i = (int) sizeOf_training_data, c = 0; i < size_of_data; i++, c++) {
			for (int k = 0; k < ValuesRange.size(); k++) {
				for (int j = 0; j < sizeOf_training_data; j++) {
					if ((MaintenancePrice.get(i).equals(MaintenancePrice.get(j)))
							&& (CarAcceptability.get(j).equals(ValuesRange.get(k))))
						counter++;
				}
				P_F2[c][k] = (double) counter / NumOFValuesRange.get(k);
				counter = 0;
			}
		}
		// Compute To Testing Data The Expected Class For Number Of Doors
		counter = 0;
		for (int i = (int) sizeOf_training_data, c = 0; i < size_of_data; i++, c++) {
			for (int k = 0; k < ValuesRange.size(); k++) {
				for (int j = 0; j < sizeOf_training_data; j++) {
					if ((NumberOfDoors.get(i).equals(NumberOfDoors.get(j)))
							&& (CarAcceptability.get(j).equals(ValuesRange.get(k))))
						counter++;
				}
				P_F3[c][k] = (double) counter / NumOFValuesRange.get(k);
				counter = 0;
			}
		}
		// Compute To Testing Data The Expected Class For Capacity in terms of persons
		// to carry
		counter = 0;
		for (int i = (int) sizeOf_training_data, c = 0; i < size_of_data; i++, c++) {
			for (int k = 0; k < ValuesRange.size(); k++) {
				for (int j = 0; j < sizeOf_training_data; j++) {
					if ((CapacityInTermsOfPersonsToCarry.get(i).equals(CapacityInTermsOfPersonsToCarry.get(j)))
							&& (CarAcceptability.get(j).equals(ValuesRange.get(k))))
						counter++;
				}
				P_F4[c][k] = (double) counter / NumOFValuesRange.get(k);
				counter = 0;
			}
		}
		// Compute To Testing Data The Expected Class For the size of luggage boot
		counter = 0;
		for (int i = (int) sizeOf_training_data, c = 0; i < size_of_data; i++, c++) {
			for (int k = 0; k < ValuesRange.size(); k++) {
				for (int j = 0; j < sizeOf_training_data; j++) {
					if ((SizeOfLuggageBoot.get(i).equals(SizeOfLuggageBoot.get(j)))
							&& (CarAcceptability.get(j).equals(ValuesRange.get(k))))
						counter++;
				}
				P_F5[c][k] = (double) counter / NumOFValuesRange.get(k);
				counter = 0;
			}
		}
		// Compute To Testing Data The Expected Class For Estimated safety of the car
		counter = 0;
		for (int i = (int) sizeOf_training_data, c = 0; i < size_of_data; i++, c++) {
			for (int k = 0; k < ValuesRange.size(); k++) {
				for (int j = 0; j < sizeOf_training_data; j++) {
					if ((EstimatedSafetyOfTheCar.get(i).equals(EstimatedSafetyOfTheCar.get(j)))
							&& (CarAcceptability.get(j).equals(ValuesRange.get(k))))
						counter++;
				}
				P_F6[c][k] = (double) counter / NumOFValuesRange.get(k);
				counter = 0;
			}
		}
		// Re-Correct the Zero's of the probability and make it 0.001
		String str;
		for (int i = 0; i < sizeOf_testing_data; i++) {
			for (int j = 0; j < 4; j++) {
				if (P_F1[i][j] == 0.0)
					P_F1[i][j] = 0.001;
				if (P_F2[i][j] == 0.0)
					P_F2[i][j] = 0.001;
				if (P_F3[i][j] == 0.0)
					P_F3[i][j] = 0.001;
				if (P_F4[i][j] == 0.0)
					P_F4[i][j] = 0.001;
				if (P_F5[i][j] == 0.0)
					P_F5[i][j] = 0.001;
				if (P_F6[i][j] == 0.0)
					P_F6[i][j] = 0.001;
			}
		}
		// Determine the the class of each row in the Testing data set
		List<Double> help1 = new ArrayList<>();
		List<Double> help2 = new ArrayList<>();
		double help3;
		counter = 0;
		for (int i = 0; i < sizeOf_testing_data; i++) {
			for (int j = 0; j < 4; j++) {
				help3 = P_F1[i][j] * P_F2[i][j] * P_F3[i][j] * P_F4[i][j] * P_F5[i][j] * P_F6[i][j]; 
				help3 *= Probability.get(j);
				help1.add(help3);
				help2.add(help3);
				help3 = 1;
			}
			Collections.sort(help2);
			int index = help1.indexOf(help2.get(help2.size()-1));
			unExpected.add(ValuesRange.get(index));
			//System.out.println(help2);
			help1.clear();
			help2.clear();
			if (unExpected.get(i).equals(CarAcceptability.get((int)sizeOf_training_data+i))) {
				counter ++;
				}
		}
		double BayesianAccuracy = (double) counter / sizeOf_testing_data;
		System.out.println( "Bayesian Algorithm Accuracy: "+BayesianAccuracy);
		//////////////////////////////////////////////////////////////////////////////////////////////////////////
		//////////////////////////////////////////////////////////////////////////////////////////////////////////
		// K-Nearest Algorithm 
		// Convert The String to Integer
		for (int i = 0 ; i < size_of_data ; i++ ) {
			// Feature 1
			if (BuyingPrice.get(i).equals("low"))
				ValueOfBuying.add(1);
			else if(BuyingPrice.get(i).equals("med"))
				ValueOfBuying.add(2);
			else if(BuyingPrice.get(i).equals("high"))
				ValueOfBuying.add(3);
			else if(BuyingPrice.get(i).equals("vhigh"))
				ValueOfBuying.add(4);
			// Feature 2
			if (MaintenancePrice.get(i).equals("low"))
				ValueOfMaintenance.add(1);
			else if(MaintenancePrice.get(i).equals("med"))
				ValueOfMaintenance.add(2);
			else if(MaintenancePrice.get(i).equals("high"))
				ValueOfMaintenance.add(3);
			else if(MaintenancePrice.get(i).equals("vhigh"))
				ValueOfMaintenance.add(4);
			// Feature 3
			if (NumberOfDoors.get(i).equals("2"))
				ValueOfDoors.add(2);
			else if(NumberOfDoors.get(i).equals("3"))
				ValueOfDoors.add(3);
			else if(NumberOfDoors.get(i).equals("4"))
				ValueOfDoors.add(4);
			else if(NumberOfDoors.get(i).equals("5more"))
				ValueOfDoors.add(5);
			// Feature 4
			if (CapacityInTermsOfPersonsToCarry.get(i).equals("2"))
				ValueOfCapacity.add(2);
			else if(CapacityInTermsOfPersonsToCarry.get(i).equals("4"))
				ValueOfCapacity.add(4);
			else if(CapacityInTermsOfPersonsToCarry.get(i).equals("more"))
				ValueOfCapacity.add(5);
			// Feature 5
			if (SizeOfLuggageBoot.get(i).equals("small"))
				ValueOfLuggage.add(1);
			else if(SizeOfLuggageBoot.get(i).equals("med"))
				ValueOfLuggage.add(2);
			else if(SizeOfLuggageBoot.get(i).equals("big"))
				ValueOfLuggage.add(3);
			// Feature 6
			if (EstimatedSafetyOfTheCar.get(i).equals("low"))
				ValueOfEstimation.add(1);
			else if(EstimatedSafetyOfTheCar.get(i).equals("med"))
				ValueOfEstimation.add(2);
			else if(EstimatedSafetyOfTheCar.get(i).equals("high"))
				ValueOfEstimation.add(3);
		}
		//System.out.println(ValueOfBuying);
		//System.out.println(ValueOfMaintenance);
		//System.out.println(ValueOfCapacity);
		//System.out.println(ValueOfDoors);
		//System.out.println(ValueOfEstimation);
		//System.out.println(ValueOfLuggage);
		// Compute The Distance for Every Tuple
		double  distance1 , distance2;
		List <Double> Distance = new ArrayList<>();
		List <Double> Distance2 = new ArrayList<>();
		unExpected.clear();
		for (int i = (int)sizeOf_training_data ; i < size_of_data ; i++) { // Testing Data
			for (int j = 0 ; j < sizeOf_training_data ; j++) {
				distance1 = ValueOfBuying.get(j) - ValueOfBuying.get(i);
				distance1 *= distance1;
				distance2 = distance1;
				distance1 = ValueOfCapacity.get(j) - ValueOfCapacity.get(i);
				distance1 *= distance1;
				distance2 += distance1;
				distance1 = ValueOfDoors.get(j) - ValueOfDoors.get(i);
				distance1 *= distance1;
				distance2 += distance1;
				distance1 = ValueOfEstimation.get(j) - ValueOfEstimation.get(i);
				distance1 *= distance1;
				distance2 += distance1;
				distance1 = ValueOfLuggage.get(j) - ValueOfLuggage.get(i);
				distance1 *= distance1;
				distance2 += distance1;
				distance1 = ValueOfMaintenance.get(j) - ValueOfMaintenance.get(i);
				distance1 *= distance1;
				distance2 += distance1;
				distance2 = Math.sqrt(distance2);
				//System.out.println(distance2);
				Distance.add(distance2);
				Distance2.add(distance2);
			}
			Collections.sort(Distance2);
			for (int k = 0 ; k < 5 ; k++) {
				int index = Distance.indexOf(Distance2.get(k));
				unExpected.add(CarAcceptability.get(index));
			}
			Distance2.clear();
			Distance.clear();
		}
		List<String> TestClass = new ArrayList<>();
		int KClass = 5 , Cunacc = 0 , Cacc = 0  , Cgood = 0  , Cvgood = 0 ;
		for (int i = 0 ; i < unExpected.size() ; i+=5) {
			for (int k = i ; k < KClass  ; k++ ) {
				if (unExpected.get(k).equals("unacc"))
					Cunacc += 1;
				else if (unExpected.get(k).equals("acc"))
					Cacc += 1;
				else if (unExpected.get(k).equals("good"))
					Cgood += 1;
				else if (unExpected.get(k).equals("vgood"))
					Cvgood += 1;
			}
			if ((Cunacc > Cacc) && (Cunacc > Cgood) && (Cunacc > Cvgood))
				TestClass.add("unacc");
			else if ((Cunacc < Cacc) && (Cacc > Cgood) && (Cacc > Cvgood))
				TestClass.add("acc");
			else if ((Cunacc < Cgood) && (Cacc < Cgood) && (Cgood > Cvgood))
				TestClass.add("good"); 
			else if ((Cunacc < Cvgood) && (Cacc < Cvgood) && (Cgood < Cvgood))
				TestClass.add("vgood");
			KClass += 5;
		}
		//System.out.println(TestClass);
		//System.out.println(TestClass.size());
		counter = 0;
		for (int i = (int)sizeOf_training_data , j = 0 ; i < size_of_data ;i++,j++) {
			if (CarAcceptability.get(i).equals(TestClass.get(j)))
				counter ++;
		}
		double KNearestAccuracy = (double) counter / sizeOf_testing_data;
		System.out.println( "K-nearest Algorithm Accuracy: "+ KNearestAccuracy);
		if (KNearestAccuracy > BayesianAccuracy)
			System.out.println("K-nearest Algorithm is Better than the Bayesian Algorithm");
		else if (BayesianAccuracy >= KNearestAccuracy)
			System.out.println("Bayesian Algorithm is Better than the K-nearest Alghorithm");
	}
}
