package projectCoupon.Exception;

import projectCoupon.Company.Company;

//exception to indicate a company could not be removed

public class CompanyRemovalException extends Exception {
	
	private static final long serialVersionUID = 1L;
		private Company company;

		public CompanyRemovalException(Company company) {
			this.company = company;
		}
		
		@Override
		public String getMessage() {
			
			return "Remove company "+this.company.getCompName()+" failed !";
		}
	}


