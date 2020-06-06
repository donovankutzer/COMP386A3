package TaxDemo.com.tax.domain.elements;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Opeyemi Adesina
 * @since 1.0
 */
// line 10 "../../../../../../../ump/tmp17w5ky53o/model.ump"
// line 310 "../../../../../../../ump/tmp17w5ky53o/model.ump"
public class Employee {

	//------------------------
	// MEMBER VARIABLES
	//------------------------

	//Employee Attributes
	private String name;
	private double age;
	private double grossIncome;

	//Employee Associations
	private List<Deduction> deductions;
	private Province worksIn;

	//------------------------
	// CONSTRUCTOR
	//------------------------

	public Employee(String aName, double aAge, double aGrossIncome, Province aWorksIn) {
		name = aName;
		age = aAge;
		grossIncome = aGrossIncome;
		deductions = new ArrayList<Deduction>();
		if (!setWorksIn(aWorksIn)) {
			throw new RuntimeException("Unable to create Employee due to aWorksIn. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
		}
		if (aAge < 14) {
			throw new RuntimeException("Please provide a valid age [age>=14]");
		}
		if (aGrossIncome < 0) {
			throw new RuntimeException("Please provide a valid grossIncome [grossIncome>=0]");
		}
	}

	//------------------------
	// INTERFACE
	//------------------------

	/* Code from template association_MinimumNumberOfMethod */
	public static int minimumNumberOfDeductions() {
		return 0;
	}

	public boolean setName(String aName) {
		boolean wasSet = false;
		name = aName;
		wasSet = true;
		return wasSet;
	}

	public boolean setAge(double aAge) {
		boolean wasSet = false;
		if (aAge >= 14) {
			age = aAge;
			wasSet = true;
		}
		return wasSet;
	}

	public boolean setGrossIncome(double aGrossIncome) {
		boolean wasSet = false;
		if (aGrossIncome >= 0) {
			grossIncome = aGrossIncome;
			wasSet = true;
		}
		return wasSet;
	}

	/**
	 * class attributes
	 */
	public String getName() {
		return name;
	}

	public double getAge() {
		return age;
	}

	public double getGrossIncome() {
		return grossIncome;
	}

	/* Code from template association_GetMany */
	public Deduction getDeduction(int index) {
		Deduction aDeduction = deductions.get(index);
		return aDeduction;
	}

	/**
	 * employees can't owe after working
	 */
	public List<Deduction> getDeductions() {
		List<Deduction> newDeductions = Collections.unmodifiableList(deductions);
		return newDeductions;
	}

	public int numberOfDeductions() {
		int number = deductions.size();
		return number;
	}

	public boolean hasDeductions() {
		boolean has = deductions.size() > 0;
		return has;
	}

	public int indexOfDeduction(Deduction aDeduction) {
		int index = deductions.indexOf(aDeduction);
		return index;
	}

	/* Code from template association_GetOne */
	public Province getWorksIn() {
		return worksIn;
	}

	/* Code from template association_AddManyToOne */
	public Deduction addDeduction() {
		return new Deduction(this);
	}

	public boolean addDeduction(Deduction aDeduction) {
		boolean wasAdded = false;
		if (deductions.contains(aDeduction)) {
			return false;
		}
		Employee existingEmployee = aDeduction.getEmployee();
		boolean isNewEmployee = existingEmployee != null && !this.equals(existingEmployee);
		if (isNewEmployee) {
			aDeduction.setEmployee(this);
		} else {
			deductions.add(aDeduction);
		}
		wasAdded = true;
		return wasAdded;
	}

	public boolean removeDeduction(Deduction aDeduction) {
		boolean wasRemoved = false;
		//Unable to remove aDeduction, as it must always have a employee
		if (!this.equals(aDeduction.getEmployee())) {
			deductions.remove(aDeduction);
			wasRemoved = true;
		}
		return wasRemoved;
	}

	/* Code from template association_AddIndexControlFunctions */
	public boolean addDeductionAt(Deduction aDeduction, int index) {
		boolean wasAdded = false;
		if (addDeduction(aDeduction)) {
			if (index < 0) {
				index = 0;
			}
			if (index > numberOfDeductions()) {
				index = numberOfDeductions() - 1;
			}
			deductions.remove(aDeduction);
			deductions.add(index, aDeduction);
			wasAdded = true;
		}
		return wasAdded;
	}

	public boolean addOrMoveDeductionAt(Deduction aDeduction, int index) {
		boolean wasAdded = false;
		if (deductions.contains(aDeduction)) {
			if (index < 0) {
				index = 0;
			}
			if (index > numberOfDeductions()) {
				index = numberOfDeductions() - 1;
			}
			deductions.remove(aDeduction);
			deductions.add(index, aDeduction);
			wasAdded = true;
		} else {
			wasAdded = addDeductionAt(aDeduction, index);
		}
		return wasAdded;
	}

	/* Code from template association_SetUnidirectionalOne */
	public boolean setWorksIn(Province aNewWorksIn) {
		boolean wasSet = false;
		if (aNewWorksIn != null) {
			worksIn = aNewWorksIn;
			wasSet = true;
		}
		return wasSet;
	}

	public void delete() {
		for (int i = deductions.size(); i > 0; i--) {
			Deduction aDeduction = deductions.get(i - 1);
			aDeduction.delete();
		}
		worksIn = null;
	}

	// line 25 "../../../../../../../ump/tmp17w5ky53o/model.ump"
	public String getProvinceName() {
		return worksIn.getName();
	}


	/**
	 * operation computes the sum of deductions for an employee
	 *
	 * @return totalDeduction the sum of provincial, federal taxes, ei and cpp
	 */
	// line 35 "../../../../../../../ump/tmp17w5ky53o/model.ump"
	public double totalDeductions() {
		double totalDeduction = 0;
		for (Deduction deduction : deductions) {
			totalDeduction += deduction.getAmount();
		}
		DecimalFormat df = new DecimalFormat("#.00");
		totalDeduction = Double.parseDouble(df.format(totalDeduction));
		return totalDeduction;
	}


	/**
	 * operation computes the sum of deductions for an employee
	 *
	 * @return totalDeduction - the sum of provincial, federal taxes, ei and cpp
	 */
	// line 48 "../../../../../../../ump/tmp17w5ky53o/model.ump"
	public double netIncome() {
		return grossIncome - totalDeductions();
	}


	public String toString() {
		return "[" +
				"name" + ":" + getName() + "," +
				"age" + ":" + getAge() + "," +
				"grossIncome" + ":" + getGrossIncome() + "]\n" +
				"  " + "worksIn = " + (getWorksIn() != null ? getWorksIn().toString() : "null");
	}
}