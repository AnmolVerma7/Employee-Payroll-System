package Model;

/**
 * Employee class 
 */
public class Employee {
	
	/**
	 * Base structure for the employee objects we will create. Every employee will have these variables. 
	 */
	private int empNo;
	private String empName;
	private String department;
	private char type; 
	private double payRate;
	private double maxHours;
	int deductions;

/**
 * This default constructor sets all numerical values to 0 and strings to "null" 	
 */
	public Employee(){
		empNo = 0;
		empName = null;
		department = null;
		type = 0; 
		payRate = 0; 
		maxHours = 0;
	}
	/**
	 * constructor sets the instance variables from the parameters 
	 * @param empNo
	 * @param empName
	 * @param department
	 * @param type
	 * @param payRate
	 * @param maxHours
	 */
	public Employee(int empNo, String empName, String department, 
			char type, double payRate, double maxHours) {
		this.empNo = empNo;
		this.empName = empName;
		this.department = department;
		this.type = type;
		this.payRate = payRate;
		this.maxHours = maxHours;
	}
	
	/**
	 * Copy constructor will take employee e's type, pay-rate, and max-hours. user will input number, name and department
	 * @param e
	 * @param empNo
	 * @param empName
	 * @param department
	 */
	public Employee(Employee e,int empNo, String empName, String department){
		this.empNo = empNo;
		this.empName= empName;
		this.department=department;
		this.type=e.getType();
		this.payRate=e.getPayRate(); 
		this.maxHours=e.getMaxHours();
			
	}
	
	/*
	 * This accessor method gets the employee number
	 */
	public int getEmpNo() {
		return empNo;
	}

	/*
	 * This mutator method sets the employee number
	 */
	public void setEmpNo(int empNo) {
		this.empNo = empNo;
	
	}

	/*
	 * This accessor method gets the employee name
	 */
	public String getEmpName() {
		return empName;
	}

	/*
	 * This mutator method sets the employee name
	 */
	public void setEmpName(String empName) {
		this.empName = empName;
	}

	/*
	 * This accessor method gets the department
	 */
	public String getDepartment() {
		return department;
	}

	/*
	 * This mutator method sets the department
	 */
	public void setDepartment(String department) {
		this.department = department;
	}
	
	/*
	 * This accessor method gets the type of wage
	 */
	public char getType() {
		return type;
	}
	
	
	/*
	 * This mutator method sets the type of wage
	 */
	public void setType(char type) {
		this.type = type;
	}
	
	/*
	 * This accessor method gets the pay rate
	 */
	public double getPayRate() {
		return payRate;
	}

	/*
	 * This mutator method sets the pay rate
	 */
	public void setPayRate(double payRate) {
		this.payRate = payRate;
	}
	
	/*
	 * This accessor method gets the max hours an employee can work
	 */
	public double getMaxHours() {
		return maxHours;
	}
	
	/*
	 * This mutator method sets the max hours an employee can work 
	 */
	public void setMaxHours(double maxHours) {
		this.maxHours = maxHours;
	}

	@Override
	/**
	 * Returns the employee object in a string format, 1 entry per line
	 */
	public String toString() {
		return "Employee "
				+ "\n Employee Number: " + empNo
				+ "\n Employee Name:   " + empName 
				+ "\n Employee Type:   " + type
				+ "\n Department:      " + department 
				+ "\n Pay Rate:        " + payRate 
				+ "\n Max Hours        " + maxHours + "\n";
	}

}
