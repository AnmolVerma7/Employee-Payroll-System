package Controller;

import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

import Model.Employee;

/**
 * The controller class of the Payroll Application 
 */
public class FlyByNightController {
	
	/**
	 * File path to the text file
	 * ArrayList object of object Employee
	 * Scanner object
	 */
	private final String FILE_PATH = "res/ETD.txt";
	public ArrayList<Employee> employees;
	private Scanner input = new Scanner(System.in);
	
	/**
	 * Main controller. 
	 * @throws Exception
	 */
	
	public FlyByNightController() throws Exception {
		employees = new ArrayList<Employee>();
		loadData();
		processingMethods(employees);
	}
	/**
	 * Load function pulls from text file (FILE_PATH)
	 * New employee created for each line, parsed to assign correct array values. 
	 * @throws Exception
	 */
	public void loadData() throws Exception{
		File file = new File(FILE_PATH);
		String currentLine;
		String [] splittedLine;
		Employee emp;
		
		if(file.exists()) {
			Scanner fileReader = new Scanner(file);
			
			while (fileReader.hasNextLine()) {
				currentLine = fileReader.nextLine(); 
				splittedLine = currentLine.split("\\s+");
				if (currentLine != null) {
					emp = new Employee(Integer.parseInt(splittedLine[0]),splittedLine[1],splittedLine[2],
							splittedLine[3].charAt(0), Double.parseDouble(splittedLine[4]), 
							Double.parseDouble(splittedLine[5]));
					
					employees.add(emp); 
				}
			}
			
			fileReader.close();
		}
	}	
	
	
	public void processingMethods(ArrayList<Employee> list) {
		
		double grossPay = 0;
		double witholdingTax = 0;
		double canadaPensionPlan = 0;
		double employmentInsurance = 0;
		double extendHealthBenefit = 0;
		double unionDues = 0;
		int id=0;
		double netPay = 0;
		String name;
		String empType = null;
		for (Employee currentEmployee: list) {
			
			if(currentEmployee.getType()=='S') {
				empType="Salary";
			}
			else if(currentEmployee.getType()=='H') {
				empType="Hourly";
			}
			else if(currentEmployee.getType()=='C') {
				empType="Consultant";
			}
			
			id=currentEmployee.getEmpNo();
			name = currentEmployee.getEmpName();
			grossPay = calcGrossPay(currentEmployee, currentEmployee.getMaxHours());
			netPay= calcNetPay(currentEmployee, currentEmployee.getMaxHours());
			witholdingTax = calcWithhold(grossPay);
			canadaPensionPlan = calcCPP(grossPay);
			employmentInsurance = calcEI(grossPay);
						
			if (currentEmployee.getType() == 'S' || currentEmployee.getType() == 'H') {
				extendHealthBenefit = calcExtHealth(grossPay);
			}
			// else no deduction
			else {
				extendHealthBenefit = 0;
			}
			// If employee is hourly, union dues are deducted from gross pay
			if (currentEmployee.getType() == 'H') {
				unionDues = calcUnionDues(grossPay);
			}
			// else no deduction
			else {
				unionDues = 0;
			}
			System.out.println("Name: " + name);
			System.out.println("Employee Number: " + id);
			System.out.println("Employee Type: " + empType);

			System.out.println("Gross weekly pay: " + grossPay);
			System.out.println("Incoming witholding Tax: " + witholdingTax);
			System.out.println("Canada Pension Plan (CPP) contribution: " + canadaPensionPlan);
			System.out.println("Employment Insurance (EI) Contribution: " + employmentInsurance);
			System.out.println("Extended Health Benefit: " + extendHealthBenefit);
			System.out.println("Unions Dues: " + unionDues);
			System.out.println("Net weekly pay: " + netPay+ "\n");

		}
		
		
	}
	/**
	 * Calculates the gross pay for various positions
	 * @param e
	 * @param hoursWorked - processed hourly limitations for each type
	 * @return earnings 
	 */
	public double calcGrossPay(Employee e, double hoursWorked) {
		double payRate = e.getPayRate();
		double maxHours = hoursWorked;
		char type = e.getType();
		double basePay = 0;
		double overtimePay = 0;
		double earnings = 0;
		
		if (type == 'S') {
			basePay = payRate/52;
			return basePay; 
		}
		else if (type == 'H'){
			if (maxHours > 40) {
				basePay = payRate * 40;
				overtimePay = (payRate/2 + payRate) * (maxHours - 40);
				earnings = basePay + overtimePay;
				return earnings;
			}
			else {
				 return maxHours * payRate;
			}			
		}
		else if (type == 'C') {
			if (maxHours > 40) {
				basePay = payRate * 40;
				return basePay;
			}				
			else {
				basePay = payRate * maxHours;
				return basePay;
			}
		}
		return earnings;
	}
	/**
	 * Calculates various Federal tax deductions for each income range
	 * @param grossPay
	 * @return
	 */
	public double calcWithhold(double grossPay) {
		if (grossPay < 1000) {
			return grossPay = (grossPay * 0.075);
		}
		
		else if (grossPay > 1000 && grossPay < 2000) {
			return grossPay = (grossPay * 0.12);
		}
		
		else if (grossPay > 2000) {
			return grossPay = (grossPay * 0.17);
		}
		return 0;
	}
	
	/**
	 * Calculates CPP
	 * @param grossPay
	 * @return
	 */
	public double calcCPP(double grossPay) {
		return grossPay = (grossPay * 0.0475);
	}
	
	/**
	 * Calculates EI deductions
	 * @param grossPay
	 * @return
	 */
	public double calcEI(double grossPay) {
		return grossPay = (grossPay * 0.018);
	}
	
	/**
	 * Health deductions
	 * @param grossPay
	 * @return
	 */
	public double calcExtHealth(double grossPay) {
		return grossPay * 0.013;
	}
	
	/**
	 * Union dues
	 * @param grossPay
	 * @return
	 */
	public double calcUnionDues(double grossPay) {
		return grossPay * 0.01;
	}
	
	/**
	 * Calculates net pay for various worker positions
	 * @param e
	 * @param hoursWorked
	 * @return
	 */
	public double calcNetPay(Employee e, double hoursWorked) {
		double maxHours = hoursWorked;
		double netPay=0;
		double deductions=0;
		char type = e.getType();
		double gross = calcGrossPay(e,maxHours);

		if(type=='S') {
			deductions = calcWithhold(gross) + calcCPP(gross) + calcEI(gross) + calcExtHealth(gross);
			return netPay = gross - deductions;
			
		}
		else if (type =='H') {
			deductions = calcWithhold(gross) + calcCPP(gross) + calcEI(gross) + calcExtHealth(gross) + calcUnionDues(gross);
			return netPay = gross - deductions;

		}
		else if (type =='C') {
			deductions = calcWithhold(gross) + calcCPP(gross) + calcEI(gross);
			return netPay = gross - deductions;
		}
		
			return netPay = gross - deductions;
	}
	
	/**
	 * Comparison method. 
	 * @param e
	 * @param f
	 * @return returns -1,0,1 based on comparing the two employee numbers
	 */
	public int compareTo(Employee e,Employee f) {
		int empNo =e.getEmpNo();
		int otherEmpNo =f.getEmpNo();
		int compare = 0;
		
		if(empNo < otherEmpNo) {
			return compare = -1;
		}
		else if(empNo == otherEmpNo){
			return compare = 0;
		}
		else if(empNo > otherEmpNo) {
			return compare = 1;
		}
		return compare;
		
	}

}
