package Exception;

import projectCoupon.Company.Company;

public class CompanyUpdateException extends Exception {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private Company company;
		
		public CompanyUpdateException(Company company) {
			this.company = company;
		}
		
		@Override
		public String getMessage() {

			return "Update for company "+this.company.getCompName()+" failed !";
		}
	}


