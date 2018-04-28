angular
		.module('couponSystem', [ 'ngResource' ])
		.controller('couponController',
				function($resource) {

					var c = this;

					c.adminLoginResource = $resource(
							"api/admin/login/:username/:password", {
								"username" : "@username",
								"password" : "@password"
							});

					c.companyLoginResource = $resource(
							"api/company/login/:username/:password", {
								"username" : "@username",
								"password" : "@password"
							});

					c.customerLoginResource = $resource(
							"api/customer/login/:username/:password",
							{
								"username" : "@username",
								"password" : "@password"
							});

					c.loginDiv = true;
					c.adminDiv = false;			
					c.companyDiv = false;		
					c.customerDiv = false;		

					// Admin DIV

					c.createCompanyDiv = false;			
					c.removeCompanyDiv = false;			
					c.updateCompanyDiv = false;			
					c.getCompanyDiv = false;			
					c.getAllCompaniesDiv = false;		
					c.createCustomerDiv = false;		
					c.removeCustomerDiv = false;		
					c.updateCustomerDiv = false;		
					c.getCustomerDiv = false;			
					c.getAllCustomersDiv = false;		

					// Company DIV

					c.createCouponDiv = false;			
					c.removeCouponDiv = false;			
					c.updateCouponDiv = false;			
					c.getThisCompanyDiv = false;		
					c.getCouponByIdDiv = false;			
					c.getAllCouponsDiv = false;			
					c.getCouponByTypeDiv = false;		
					c.getCouponByPriceDiv = false;		
					c.getCouponByDateDiv = false;		

					// Customer DIV

					c.purchaseCouponDiv = false;				
					c.getAllPurchasedCouponsDiv = false;		
					c.getAllPurchasedCouponsByTypeDiv = false;	
					c.getAllPurchasedCouponsByPriceDiv = false;	

					// Table DIV

					c.companyTableDiv = false;				
					c.updateCompanyTableDiv = false;		
					c.getCompanyTableDiv = false;			
					c.getAllCompaniesTableDiv = false;		
					c.customerTableDiv = false;				
					c.updateCustomerTableDiv = false;		
					c.getCustomerTableDiv = false;			
					c.getAllCustomersTableDiv = false;		
					
					c.createCouponTableDiv = false;			
					c.updateCouponTableDiv = false;			
					c.getMyCompanyTableDiv = false;						
					c.getCouponByIdTableDiv = false;		
					c.getAllCouponsTableDiv = false;		
					c.couponByTypeTableDiv = false;			
					c.couponByPriceTableDiv = false;		
					c.couponByDateTableDiv = false;			
					
					c.getAllSystemCouponsTableDiv = false;		
					c.purchaseCouponSuccessDiv = false;			
					c.getAllPurchasedCouponsTableDiv = false;	
					c.purchasedCouponByTypeTableDiv = false;	
					c.purchasedCouponByPriceTableDiv = false;	
					
					
					// Exceptions DIV
					
					c.loginExceptionDiv = false;
					
					//Admin
					c.companyExceptionDiv = false;				
					c.removeCompanySuccessDiv = false;			
					c.removeCompanyExceptionDiv = false;		
					c.showAllCompaniesExceptionDiv = false;		
					c.getCompanyExceptionDiv = false;			
					c.getAllCompaniesExceptionDiv = false;		
					c.customerExceptionDiv = false;				
					c.removeCustomerSuccessDiv = false;			
					c.removeCustomerExceptionDiv = false;		
					c.showAllCustomersExceptionDiv = false;		
					c.updateCustomerExceptionDiv = false;		
					c.getCustomerExceptionDiv = false;			
					c.getAllCustomersExceptionDiv = false;		
					
					//Company
					c.createCouponExceptionDiv = false;			
					c.removeCouponSuccessDiv = false;			
					c.getAllCompanyCouponsExceptionDiv = false	
					c.removeCouponExceptionDiv = false;			
					c.updateCouponExceptionDiv = false;			
					c.getMyCompanyExceptionDiv = false;			
					c.getCouponByIdExceptionDiv = false;		
					c.getAllCouponsExceptionDiv = false;		
					c.couponByTypeExceptionDiv = false;						
					c.couponByPriceExceptionDiv = false;		
					c.couponByDateExceptionDiv = false;			
					
					//Customer
					c.getAllSystemCouponExceptionDiv = false;		
					c.getAllPurchasedCouponsExceptionDiv = false;	
					c.purchasedCouponByTypeExceptionDiv = false;	
					c.purchasedCouponByPriceExceptionDiv = false;	
					
					

					c.loginFields = {
						"username" : "",
						"password" : "",
						"clientType" : ""
					};

					c.companyFields = {
						"id" : null,
						"compName" : "",
						"password" : "",
						"email" : ""
					};
					
					c.customerFields = {
						"id" : null,
						"custName" : "",
						"password" : ""
					}
					
					c.couponFields = {
						"id" : null,
						"title" : "",
						"startDate" : "",
						"endDate" : "",
						"amount" : "",
						"type" : "",
						"message" : "",
						"price" : "",
						"image" : ""
					}
					
					c.infoMessage = "";
					c.errorMessage = "";

					c.info = function(message) {
						c.infoMessage = message;
						c.errorMessage = "";
					}

					c.error = function(message) {
						c.infoMessage = "";
						c.errorMessage = message;
					}

					c.handleError = function(response, defaultMessage) {
						if (response.data != null && response.data.message != null) {
							c.error("Error: " + response.data.message);
						} else {
							c.error(defaultMessage);
						}
					}

					c.submitLoginForm = function() {
						if (c.loginFields.clientType == "Admin") {
							c.adminLoginResource.get(c.loginFields,
									function(response) {
										c.adminDiv = true;				
										c.loginDiv = false;
										c.companyDiv = false;			
										c.customerDiv = false;			
										
										c.createCompanyDiv = false;		
										c.removeCompanyDiv = false;		
										c.updateCompanyDiv = false;		
										c.getCompanyDiv = false;		
										c.getAllCompaniesDiv = false;	
										c.createCustomerDiv = false;	
										c.removeCustomerDiv = false;	
										c.updateCustomerDiv = false;	
										c.getCustomerDiv = false;		
										c.getAllCustomersDiv = false;	
									}, function(response, defaultMessage) {
										c.handleError(response, "Login Failed");
										c.loginExceptionDiv = true;
									});
						} else if (c.loginFields.clientType == "Company") {
							c.companyLoginResource.get(
									c.loginFields, function(response) {
										c.loginFields.username;
										c.companyDiv = true;			
										c.loginDiv = false;
										c.adminDiv = false;				
										c.customerDiv = false;			
										
										c.createCouponDiv = false;		
										c.removeCouponDiv = false;		
										c.updateCouponDiv = false;		
										c.getThisCompanyDiv = false;	
										c.getCouponByIdDiv = false;		
										c.getAllCouponsDiv = false;		
										c.getCouponByTypeDiv = false;	
										c.getCouponByPriceDiv = false;	
										c.getCouponByDateDiv = false;	
									}, function(response, defaultMessage) {
										c.handleError(response, "Login Failed");
										c.loginExceptionDiv = true;
									});
						} else if (c.loginFields.clientType == "Customer") {
							c.customerLoginResource.get(
									c.loginFields, function(response) {
										c.loginFields.username;
										c.customerDiv = true;				
										c.loginDiv = false;
										c.adminDiv = false;					
										c.companyDiv = false;				
										
										c.purchaseCouponDiv = false;				
										c.getAllSystemCouponsTableDiv = false;		
										c.purchaseCouponSuccessDiv = false;			
										c.getAllPurchasedCouponsDiv = false;		
										c.getAllPurchasedCouponsByTypeDiv = false;	
										c.getAllPurchasedCouponsByPriceDiv = false;	
									}, function(response, defaultMessage) {
										c.handleError(response, "Login Failed");
										c.loginExceptionDiv = true;
									});
						}
					}

					c.adminLogoutResource = $resource("api/admin/logout");

					c.adminLogoutButton = function() {
						c.adminLogoutResource.get(function(response) {
							c.loginFields = {
								"username" : "",
								"password" : "",
								"clientType" : ""
							};
							c.loginDiv = true;
							c.loginExceptionDiv = false;
							c.customerDiv = false;				
							c.adminDiv = false;					
							c.companyDiv = false;				
						});
					}

					c.companyLogoutResource = $resource("api/company/logout");

					c.companyLogoutButton = function() {
						c.companyLogoutResource.get(function(response) {
							c.loginFields = {
								"username" : "",
								"password" : "",
								"clientType" : ""
							};
							c.loginDiv = true;
							c.loginExceptionDiv = false;
							c.customerDiv = false;				
							c.adminDiv = false;					
							c.companyDiv = false;				
						});
					}

					c.customerLogoutResource = $resource("api/customer/logout");

					c.customerLogoutButton = function() {
						c.customerLogoutResource.get(function(response) {
							c.loginFields = {
								"username" : "",
								"password" : "",
								"clientType" : ""
							};
							c.loginDiv = true;
							c.loginExceptionDiv = false;
							c.customerDiv = false;				
							c.adminDiv = false;					
							c.companyDiv = false;				
						});
					}

//=============================================================================================================================
					
					//Admin resource for companies management
					c.adminCompaniesResource = $resource("api/admin/companies/:id",{'id':'@id'},{
					"updateCompany": {method:"PUT"}
					});
				
					c.adminAllCompaniesResource = $resource("api/admin/companies");
					
					//Admin recourse for customers management
					c.adminCustomersResource = $resource("api/admin/customers/:id",{'id':'@id'},{
					"updateCustomer": {method:"PUT"}
					});
				
					c.adminAllCustomersResource = $resource("api/admin/customers");
					
					c.createCompanyForm = function() {
						c.adminCompaniesResource.save(c.companyFields, function(company) {
							c.companyFields.id = company.id;
							c.companyFields.compName = company.compName;
							c.companyFields.email = company.email;
							c.companyTableDiv = true;			
							c.companyExceptionDiv = false;		
						}, function(response, defaultMessage) {
							c.handleError(response, "Company Creation Failed");
							c.companyExceptionDiv = true;		
							c.companyTableDiv = false;			
						});
					}
					
					c.showAllCompanies = [];
					
					c.showAllCompaniesForm = function() {
						c.showAllCompanies = c.adminAllCompaniesResource.query(c.companyFields, function() {
							if (c.showAllCompanies.length > 0) {
								c.showAllCompanies.forEach(function(company){
									c.showAllCompaniesTableDiv = true;								
									c.showAllCompaniesExceptionDiv = false;			
									c.removeCompanySuccessDiv = false;				
									c.removeCompanyExceptionDiv = false;			
									})
							} else {
								c.error("No companies were found");
								c.showAllCompaniesExceptionDiv = true;				
								c.showAllCompaniesTableDiv = false;					
								c.removeCompanySuccessDiv = false;					
								c.removeCompanyExceptionDiv = false;				
							}
							}, function() {
								c.error("Request could not be completed");
								c.showAllCompaniesExceptionDiv = true;				
								c.showAllCompaniesTableDiv = false;					
								c.removeCompanySuccessDiv = false;					
								c.removeCompanyExceptionDiv = false;				
							});
					}
					
					c.removeCompanyForm = function(company) {
						c.adminCompaniesResource.delete(company, function() {
							c.info("Operation completed successfully");
							c.removeCompanySuccessDiv = true;						
							c.removeCompanyExceptionDiv = false;					
							c.showAllCompaniesTableDiv = false;						
							c.showAllCompaniesExceptionDiv = false;					
						}, function(response, defaultMessage) {
							c.handleError(response, "Request could not be completed");
							c.removeCompanyExceptionDiv = true;						
							c.removeCompanySuccessDiv = false;						
							c.showAllCompaniesTableDiv = false;						
							c.showAllCompaniesExceptionDiv = false;					
						});
					}
						
					c.updateCompanyForm = function() {
						c.adminCompaniesResource.get(c.companyFields, function(company) {
							if (c.companyFields.password != "") {
								company.password = c.companyFields.password;
							}
							if (c.companyFields.email != "") {
								company.email = c.companyFields.email;
							}
							company.$updateCompany(company, function() {
								c.companyFields.id = company.id;
								c.companyFields.compName = company.compName;
								c.companyFields.password = company.password;
								c.companyFields.email = company.email;
								c.updateCompanyTableDiv = true;					
								c.updateCompanyExceptionDiv = false;			
							})
						},function(response, defaultMessage) {
							c.handleError(response, "Request could not be completed");
							c.updateCompanyExceptionDiv = true;					
							c.updateCompanyTableDiv = false;					
						});
					}
						
					c.getCompanyForm = function() {
						c.adminCompaniesResource.get(c.companyFields, function(company) {
							c.companyFields = company; 
							c.getCompanyTableDiv = true;				
							c.getCompanyExceptionDiv = false;			
						}, function(response, defaultMessage) {
							c.handleError(response, "Request could not be completed");
							c.getCompanyExceptionDiv = true;			
							c.getCompanyTableDiv = false;				
						});
					}

					c.allCompanies = [];
					
					c.getAllCompaniesForm = function() {
						c.allCompanies = c.adminAllCompaniesResource.query(c.companyFields, function() {
							if (c.allCompanies.length > 0) {
								c.allCompanies.forEach(function(company){
									c.getAllCompaniesTableDiv = true;		
									c.getAllCompaniesExceptionDiv = false;	
									})
							} else {
								c.error("No companies were found");
								c.getAllCompaniesExceptionDiv = true;	
								c.getAllCompaniesTableDiv = false;		
							}
							}, function(response, defaultMessage) {
								c.handleError(response, "Request could not be completed");
								c.getAllCompaniesExceptionDiv = true;	
								c.getAllCompaniesTableDiv = false;		
							});
					}
					

					c.createCustomerForm = function() {
						c.adminCustomersResource.save(c.customerFields, function(customer) {
							c.customerFields.id = customer.id;
							c.customerFields.custName = customer.custName;
							c.customerTableDiv = true;		
							c.customerExceptionDiv = false;	
						}, function(response, defaultMessage) {
							c.handleError(response, "Customer Creation Failed");
							c.customerExceptionDiv = true;	
							c.customerTableDiv = false;		
						});
					}
					
					c.showAllCustomers = [];
					
					c.showAllCustomersForm = function() {
						c.showAllCustomers = c.adminAllCustomersResource.query(c.customerFields, function() {
							if (c.showAllCustomers.length > 0) {
								c.showAllCustomers.forEach(function(customer){
									c.showAllCustomersTableDiv = true;		
									c.showAllCustomersExceptionDiv = false;	
									c.removeCustomerSuccessDiv = false;		
									c.removeCustomerExceptionDiv = false;	
									})
							} else {
								c.error("No companies were found");
								c.showAllCustomersExceptionDiv = true;	
								c.showAllCustomersTableDiv = false;		
								c.removeCustomerSuccessDiv = false;		
								c.removeCustomerExceptionDiv = false;	
							}
							}, function(response, defaultMessage) {
								c.handleError(response, "Request could not be completed");
								c.showAllCustomersExceptionDiv = true;	
								c.showAllCustomersTableDiv = false;		
								c.removeCustomerSuccessDiv = false;		
								c.removeCustomerExceptionDiv = false;	
							});
					}
					
					c.removeCustomerForm = function(customer) {
						c.adminCustomersResource.delete(customer, function() {
							c.info("Operation completed successfully");
							c.removeCustomerSuccessDiv = true;		
							c.removeCustomerExceptionDiv = false;	
							c.showAllCustomersTableDiv = false;		
							c.showAllCustomersExceptionDiv = false;	
						}, function(response, defaultMessage) {
							c.handleError(response, "Request could not be completed");
							c.removeCustomerExceptionDiv = true;	
							c.removeCustomerSuccessDiv = false;		
							c.showAllCustomersTableDiv = false;		
							c.showAllCustomersExceptionDiv = false;	
						});
					}
					
					c.updateCustomerForm = function() {
						c.adminCustomersResource.get(c.customerFields, function(customer) {
							if (c.customerFields.password != "") {
								customer.password = c.customerFields.password;
							}
							updateCustomer = customer;
							updateCustomer.$updateCustomer(customer, function() {
								c.customerFields.id = customer.id;
								c.customerFields.custName = customer.custName;
								c.customerFields.password = customer.password;
								c.updateCustomerTableDiv = true;		
								c.updateCustomerExceptionDiv = false;	
							})
						},function(response, defaultMessage) {
							c.handleError(response, "Request could not be completed");
							c.updateCustomerExceptionDiv = true;	
							c.updateCustomerTableDiv = false;		
						});
					}
					
					
					c.getCustomerForm = function() {
						c.adminCustomersResource.get(c.customerFields, function(customer) {
							c.customerFields = customer; 
							c.getCustomerTableDiv = true;		
							c.getCustomerExceptionDiv = false;	
						}, function(response, defaultMessage) {
							c.handleError(response, "Request could not be completed");
							c.getCustomerTableDiv = false;		
							c.getCustomerExceptionDiv = true;	
						});
					}
					
					
					c.allCustomers = [];
					
					c.getAllCustomersForm = function() {
						c.allCustomers = c.adminAllCustomersResource.query(c.customerFields, function() {
							if (c.allCustomers.length > 0) {
								c.allCustomers.forEach(function(customer){
									c.getAllCustomersTableDiv = true;		
									c.getAllCustomersExceptionDiv = false;	
									})
							} else {
								c.error("No Customers were found");
								c.getAllCustomersExceptionDiv = true;	
								c.getAllCustomersTableDiv = false;		
							}
							}, function(response, defaultMessage) {
								c.handleError(response, "Request could not be completed");
								c.getAllCustomersExceptionDiv = true;	
								c.getAllCustomersTableDiv = false;		
							});
					}

//=============================================================================================================================
										
					
					//Company recourse for coupons management
					c.companyResourceID = $resource("api/company/coupons/:id",{'id':'@id'},{
					"updateCoupon": {method:"PUT"}
					});
					
					//Company recourse for coupons management
					c.companyResource = $resource("api/company/coupons");

					
					c.createCouponForm = function() {
						if (c.couponFields.image == "") {
							c.couponFields.image = "images/No_Image.png";
						}
						c.companyResourceID.save(c.couponFields, function(coupon) {
							c.couponFields.id = coupon.id;
							c.couponFields.title = coupon.title;
							c.couponFields.startDate = coupon.startDate;
							c.couponFields.endDate = coupon.endDate;
							c.couponFields.amount = coupon.amount;
							c.couponFields.type = coupon.type;
							c.couponFields.message = coupon.message;
							c.couponFields.price = coupon.price;
							c.couponFields.image = coupon.image;
							c.createCouponTableDiv = true;		
							c.createCouponExceptionDiv = false;	
						}, function(response, defaultMessage) {
							c.handleError(response, "Request could not be completed");
							c.createCouponExceptionDiv = true;	
							c.createCouponTableDiv = false;		
						});
					}
					
					
					c.showAllCouponsResource = $resource("api/company/allcoupons");
					
					c.showAllCoupons = [];
					
					c.showAllCompanyCouponsForm = function() {
						c.showAllCoupons = c.showAllCouponsResource.query(c.couponFields, function() {
							if (c.showAllCoupons.length > 0) {
								c.showAllCoupons.forEach(function(coupon){
									c.getAllCompanyCouponsTableDiv = true;		
									c.getAllCompanyCouponsExceptionDiv = false;	
									c.removeCouponSuccessDiv = false;			
									c.removeCouponExceptionDiv = false;			
									})
							} else {
								c.error("No coupons were found");
								c.getAllCompanyCouponsExceptionDiv = true;	
								c.getAllCompanyCouponsTableDiv = false;		
								c.removeCouponSuccessDiv = false;			
								c.removeCouponExceptionDiv = false;			
							}
							}, function(response, defaultMessage) {
								c.handleError(response, "Request could not be completed");
								c.getAllCompanyCouponsExceptionDiv = true;	
								c.getAllCompanyCouponsTableDiv = false;		
								c.removeCouponSuccessDiv = false;			
								c.removeCouponExceptionDiv = false;			
							});
					}
					
					c.removeCouponForm = function(coupon) {
						c.companyResourceID.delete(coupon, function() {
							c.info("Operation completed successfully");
							c.removeCouponSuccessDiv = true;			
							c.removeCouponExceptionDiv = false;			
							c.getAllCompanyCouponsTableDiv = false;		
							c.getAllCompanyCouponsExceptionDiv = false;	
						}, function(response, defaultMessage) {
							c.handleError(response, "Request could not be completed");
							c.removeCouponExceptionDiv = true;			
							c.removeCouponSuccessDiv = false;			
							c.getAllCompanyCouponsTableDiv = false;		
							c.getAllCompanyCouponsExceptionDiv = false;	
						});
					}
					
					c.updateCouponForm = function() {
						c.companyResourceID.get(c.couponFields, function(coupon) {
							if (c.couponFields.endDate != "") {
								coupon.endDate = c.couponFields.endDate;
							}
							if (c.couponFields.price != "") {
								coupon.price = c.couponFields.price;
							}
							updateCoupon = coupon;
							updateCoupon.$updateCoupon(coupon, function() {
								c.couponFields.id = coupon.id;
								c.couponFields.title = coupon.title;
								c.couponFields.startDate = coupon.startDate;
								c.couponFields.endDate = coupon.endDate;
								c.couponFields.amount = coupon.amount;
								c.couponFields.type = coupon.type;
								c.couponFields.message = coupon.message;
								c.couponFields.price = coupon.price;
								c.couponFields.image = coupon.image;
								c.updateCouponTableDiv = true;		
								c.updateCouponExceptionDiv = false;	
							})
							}, function(response, defaultMessage) {
								c.handleError(response, "Request could not be completed");
								c.updateCouponExceptionDiv = true;	
								c.updateCouponTableDiv = false;		
							});
						}
					

					c.getMyCompanyForm = function() {
						c.companyResource.get(c.companyFields,
							function(company) {
								c.companyFields = company; 
								c.getMyCompanyTableDiv = true;		
								c.getMyCompanyExceptionDiv = false;	
						}, function(response, defaultMessage) {
								c.handleError(response, "Request could not be completed");
								c.getMyCompanyExceptionDiv = true;	
								c.getMyCompanyTableDiv = false;		
						});
					}
					
					
					c.getCouponByIdForm = function() {
						c.companyResourceID.get(c.couponFields,	function(coupon) {
							if (c.couponFields.id == null) {
								c.error("Request could not be completed");
								c.getCouponByIdExceptionDiv = true;		
								c.getCouponByIdTableDiv = false;		
							} else {
								c.couponFields = coupon;
								c.getCouponByIdTableDiv = true;			
								c.getCouponByIdExceptionDiv = false;	
							}
					}), function(response, defaultMessage) {
							c.handleError(response, "Request could not be completed");
							c.getCouponByIdExceptionDiv = true;		
							c.getCouponByIdTableDiv = false;		
						}
					}

					c.getAllCouponsResource = $resource("api/company/allcoupons");
					
					c.allCoupons = [];
					
					c.getAllCouponsForm = function() {
						c.allCoupons = c.getAllCouponsResource.query(c.couponFields, function() {
							if (c.allCoupons.length > 0) {
								c.allCoupons.forEach(function(coupon){
									c.getAllCouponsTableDiv = true;			
									c.getAllCouponsExceptionDiv = false;	
									})
							} else {
								c.error("No coupons were found");
								c.getAllCouponsExceptionDiv = true;		
								c.getAllCouponsTableDiv = false;		
							}
							}, function(response, defaultMessage) {
								c.handleError(response, "Request could not be completed");
								c.getAllCouponsExceptionDiv = true;		
								c.getAllCouponsTableDiv = false;		
							});
					}
					
					c.getCouponByTypeResource = $resource(
							"api/company/getcouponbytype/:type", {
								"type" : "@type"
							});

					c.couponByType = [];
					
					c.getCouponByTypeForm = function() {
						c.couponByType = c.getCouponByTypeResource.query(c.couponFields, function() {
							if (c.couponByType.length > 0) {
								c.couponByType.forEach(function(coupon){
									c.couponByTypeTableDiv = true;			
									c.couponByTypeExceptionDiv = false;		
									})
							} else {
								c.error("No coupons of type: " + c.couponFields.type + " were found");
								c.couponByTypeExceptionDiv = true;	
								c.couponByTypeTableDiv = false;		
							}
							}, function(response, defaultMessage) {
								c.handleError(response, "Request could not be completed");
								c.couponByTypeExceptionDiv = true;	
								c.couponByTypeTableDiv = false;		
							});
					}
					
					c.getCouponByPriceResource = $resource(
							"api/company/getcouponbyprice/:price", {
								"price" : "@price"
							});

					c.couponByPrice = [];
					
					c.getCouponByPriceForm = function() {
						c.couponByPrice = c.getCouponByPriceResource.query(c.couponFields, function() {
							if (c.couponByPrice.length > 0) {
								c.couponByPrice.forEach(function(coupon){
									c.couponByPriceTableDiv = true;			
									c.couponByPriceExceptionDiv = false;	
									})
							} else if (c.couponFields.price <= 0) {
								c.error("Price must be above 0");
								c.couponByPriceExceptionDiv = true;	
								c.couponByPriceTableDiv = false;	
							} else {
								c.error("No coupons by price: " + c.couponFields.price + " were found");
								c.couponByPriceExceptionDiv = true;	
								c.couponByPriceTableDiv = false;	
							}
							}, function(response, defaultMessage) {
								c.handleError(response, "Request could not be completed");
								c.couponByPriceExceptionDiv = true;	
								c.couponByPriceTableDiv = false;	
							});
					}
					
					c.getCouponByDateResource = $resource(
							"api/company/getcouponbydate/:endDate", {
								"endDate" : "@endDate"
							});
					
					c.couponByDate = [];
					
					c.getCouponByDateForm = function() {
						var date = new Date(c.couponFields.endDate);
						var newDate = date.toLocaleString('en-us', {year: 'numeric', month: '2-digit', day: '2-digit'}).
							replace(/(\d+)\/(\d+)\/(\d+)/, '$3-$1-$2');
						c.couponFields.endDate = newDate;
						
						c.couponByDate = c.getCouponByDateResource.query(c.couponFields, function() {
							if (c.couponByDate.length > 0) {
								c.couponByDate.forEach(function(coupon){
									c.couponByDateTableDiv = true;		
									c.couponByDateExceptionDiv = false;	
									})
							} else {
								c.error("No coupons by date: " + c.couponFields.endDate + " were found");
								c.couponByDateExceptionDiv = true;	
								c.couponByDateTableDiv = false;		
							}
							}, function(response, defaultMessage) {
								c.handleError(response, "Request could not be completed");
								c.couponByDateExceptionDiv = true;	
								c.couponByDateTableDiv = false;		
							});
					}

//=============================================================================================================================
					
					c.purchaseCouponResource = $resource("api/customer/getallsystemcoupons");
					
					c.allSystemCoupons = [];
					
					//Show all coupons
					c.showAllCouponsForm = function() {
						c.allSystemCoupons = c.purchaseCouponResource.query(c.couponFields, function() {
							if (c.allSystemCoupons.length > 0) {
								c.allSystemCoupons.forEach(function(coupon){
									c.getAllSystemCouponsTableDiv = true;		
									c.getAllSystemCouponExceptionDiv = false;	
									c.purchaseCouponSuccessDiv = false;;		
									})
							} else {
								c.error("No coupons available for purchase were found");
								c.getAllSystemCouponExceptionDiv = true;	
								c.getAllSystemCouponsTableDiv = false;		
								c.purchaseCouponSuccessDiv = false;			
							}
							}, function(response, defaultMessage) {
								c.handleError(response, "Request could not be completed");
								c.getAllSystemCouponExceptionDiv = true;	
								c.getAllSystemCouponsTableDiv = false;		
								c.purchaseCouponSuccessDiv = false;			
							});
					}
					
					c.submitPurchaseResource = $resource("api/customer/purchasecoupon");
					
					//Purchase a Coupon
					c.submitPurchaseForm = function(coupon) {
						c.submitPurchaseResource.save(coupon, function() {
							c.info("Coupon Purchased Successfully");
							c.purchaseCouponSuccessDiv = true;			
							c.getAllSystemCouponsTableDiv = false;		
							c.getAllSystemCouponExceptionDiv = false;	
						}, function(response, defaultMessage) {
							c.handleError(response, "Coupon Purchase Failed");
							c.purchaseCouponSuccessDiv = false;			
							c.getAllSystemCouponsTableDiv = false;		
							c.getAllSystemCouponExceptionDiv = true;	
						});
					}
					
					c.allPurchasedCouponsResource = $resource("api/customer/getallpurchasedcoupons");
					
					c.allPurchasedCoupons = [];
					
					c.getAllPurchasedCouponsForm = function() {
						c.allPurchasedCoupons = c.allPurchasedCouponsResource.query(c.couponFields, function() {
							if (c.allPurchasedCoupons.length > 0) {
								c.allPurchasedCoupons.forEach(function(coupon){
									c.getAllPurchasedCouponsTableDiv = true;		
									c.getAllPurchasedCouponsExceptionDiv = false;	
									})
							} else {
								c.error("No coupons were found");
								c.getAllPurchasedCouponsExceptionDiv = true;	
								c.getAllPurchasedCouponsTableDiv = false;		
							}
							}, function(response, defaultMessage) {
								c.handleError(response, "Request could not be completed");
								c.getAllPurchasedCouponsExceptionDiv = true;	
								c.getAllPurchasedCouponsTableDiv = false;		
							});
					}
					
					c.getPurchasedCouponByTypeResource = $resource(
							"api/customer/getallpurchasedcouponsbytype/:type", {
								"type" : "@type"
							});

					c.purchasedCouponByType = [];
					
					c.getPurchasedCouponByTypeForm = function() {
						c.purchasedCouponByType = c.getPurchasedCouponByTypeResource.query(c.couponFields, function() {
							if (c.purchasedCouponByType.length > 0) {
								c.purchasedCouponByType.forEach(function(coupon){
									c.purchasedCouponByTypeTableDiv = true;			
									c.purchasedCouponByTypeExceptionDiv = false;	
									})
							} else {
								c.error("No coupons of type: " + c.couponFields.type + " were found");
								c.purchasedCouponByTypeExceptionDiv = true;			
								c.purchasedCouponByTypeTableDiv = false;			
							}
							}, function(response, defaultMessage) {
								c.handleError(response, "Request could not be completed");
								c.purchasedCouponByTypeExceptionDiv = true;			
								c.purchasedCouponByTypeTableDiv = false;			
							});
					}
					
					c.getPurchasedCouponByPriceResource = $resource(
							"api/customer/getallpurchasedcouponsbyprice/:price", {
								"price" : "@price"
							});

					c.purchasedCouponByPrice = [];
					
					c.purchasedCouponByPriceForm = function() {
						c.purchasedCouponByPrice = c.getPurchasedCouponByPriceResource.query(c.couponFields, function() {
							if (c.purchasedCouponByPrice.length > 0) {
								c.purchasedCouponByPrice.forEach(function(coupon){
									c.purchasedCouponByPriceTableDiv = true;		
									c.purchasedCouponByPriceExceptionDiv = false;	
									})
							} else if (c.couponFields.price <= 0) {
								c.error("Price must be above 0");
								c.purchasedCouponByPriceExceptionDiv = true;	
								c.purchasedCouponByPriceTableDiv = false;		
							} else {
								c.error("No coupons by price: " + c.couponFields.price + " were found");
								c.purchasedCouponByPriceExceptionDiv = true;	
								c.purchasedCouponByPriceTableDiv = false;		
							}
							}, function(response, defaultMessage) {
								c.handleError(response, "Request could not be completed");
								c.purchasedCouponByPriceExceptionDiv = true;	
								c.purchasedCouponByPriceTableDiv = false;		
							});
					}

//=============================================================================================================================					
					

					c.createCompanyButton = function() {
						c.createCompanyDiv = true;			
						c.removeCompanyDiv = false;			
						c.updateCompanyDiv = false;			
						c.getCompanyDiv = false;			
						c.getAllCompaniesDiv = false;		
						c.createCustomerDiv = false;		
						c.removeCustomerDiv = false;		
						c.updateCustomerDiv = false;		
						c.getCustomerDiv = false;			
						c.getAllCustomersDiv = false;		
						c.companyFields = {
							"compName" : "",
							"password" : "",
							"email" : ""
						};
						c.companyTableDiv = false;			
						c.companyExceptionDiv = false;		
					}
					
					c.createCompanyResetButton = function() {
						c.companyFields = {
							"compName" : "",
							"password" : "",
							"email" : ""
						};
						c.companyTableDiv = false;			
						c.companyExceptionDiv = false;		
					}
					
					c.removeCompanyButton = function() {
						c.createCompanyDiv = false;			
						c.removeCompanyDiv = true;			
						c.updateCompanyDiv = false;			
						c.getCompanyDiv = false;			
						c.getAllCompaniesDiv = false;		
						c.createCustomerDiv = false;		
						c.removeCustomerDiv = false;		
						c.updateCustomerDiv = false;		
						c.getCustomerDiv = false;			
						c.getAllCustomersDiv = false;		
						c.companyFields = {
								"id" : null
						};
						c.removeCompanyExceptionDiv = false;		
						c.removeCompanySuccessDiv = false;			
						c.showAllCompaniesTableDiv = false;			
						c.showAllCompaniesExceptionDiv = false;		
					}

					c.updateCompanyButton = function() {
						c.createCompanyDiv = false;			
						c.removeCompanyDiv = false;			
						c.updateCompanyDiv = true;			
						c.getCompanyDiv = false;			
						c.getAllCompaniesDiv = false;		
						c.createCustomerDiv = false;		
						c.removeCustomerDiv = false;		
						c.updateCustomerDiv = false;		
						c.getCustomerDiv = false;			
						c.getAllCustomersDiv = false;		
						c.companyFields = {
								"compName" : "",
								"password" : "",
								"email" : ""
							};
						c.updateCompanyTableDiv = false;			
						c.updateCompanyExceptionDiv = false;		
					}
					
					c.updateCompanyResetButton = function() {
						c.companyFields = {
								"compName" : "",
								"password" : "",
								"email" : ""
							};
						c.updateCompanyTableDiv = false;			
						c.updateCompanyExceptionDiv = false;		
					}

					c.getCompanyButton = function() {
						c.createCompanyDiv = false;			
						c.removeCompanyDiv = false;			
						c.updateCompanyDiv = false;			
						c.getCompanyDiv = true;				
						c.getAllCompaniesDiv = false;		
						c.createCustomerDiv = false;		
						c.removeCustomerDiv = false;		
						c.updateCustomerDiv = false;		
						c.getCustomerDiv = false;			
						c.getAllCustomersDiv = false;		
						c.companyFields = {
								"id" : null
							};
						c.getCompanyTableDiv = false;		
						c.getCompanyExceptionDiv = false;	
					}
					
					c.getCompanyResetButton = function() {
						c.companyFields = {
								"id" : null
							};
						c.getCompanyTableDiv = false;		
						c.getCompanyExceptionDiv = false;	
					}

					c.getAllCompaniesButton = function() {
						c.createCompanyDiv = false;				
						c.removeCompanyDiv = false;				
						c.updateCompanyDiv = false;				
						c.getCompanyDiv = false;				
						c.getAllCompaniesDiv = true;			
						c.createCustomerDiv = false;			
						c.removeCustomerDiv = false;			
						c.updateCustomerDiv = false;			
						c.getCustomerDiv = false;				
						c.getAllCustomersDiv = false;			
						c.getAllCompaniesTableDiv = false;		
						c.getAllCompaniesExceptionDiv = false;	
					}

					c.createCustomerButton = function() {
						c.createCompanyDiv = false;				
						c.removeCompanyDiv = false;				
						c.updateCompanyDiv = false;				
						c.getCompanyDiv = false;				
						c.getAllCompaniesDiv = false;			
						c.createCustomerDiv = true;				
						c.removeCustomerDiv = false;			
						c.updateCustomerDiv = false;			
						c.getCustomerDiv = false;				
						c.getAllCustomersDiv = false;			
						c.customerFields = {
								"custName" : "",
								"password" : ""
						};
						c.customerTableDiv = false;			
						c.customerExceptionDiv = false;		
					}
					
					c.createCustomerResetButton = function() {
						c.customerFields = {
								"custName" : "",
								"password" : ""
						};
						c.customerTableDiv = false;			
						c.customerExceptionDiv = false;		
					}

					c.removeCustomerButton = function() {
						c.createCompanyDiv = false;			
						c.removeCompanyDiv = false;			
						c.updateCompanyDiv = false;			
						c.getCompanyDiv = false;			
						c.getAllCompaniesDiv = false;		
						c.createCustomerDiv = false;		
						c.removeCustomerDiv = true;			
						c.updateCustomerDiv = false;		
						c.getCustomerDiv = false;			
						c.getAllCustomersDiv = false;		
						c.customerFields = {
								"id" : null
						};
						c.removeCustomerExceptionDiv = false;	
						c.removeCustomerSuccessDiv = false;		
						c.showAllCustomersTableDiv = false;		
						c.showAllCustomersExceptionDiv = false;	
					}

					c.updateCustomerButton = function() {
						c.createCompanyDiv = false;				
						c.removeCompanyDiv = false;				
						c.updateCompanyDiv = false;				
						c.getCompanyDiv = false;				
						c.getAllCompaniesDiv = false;			
						c.createCustomerDiv = false;			
						c.removeCustomerDiv = false;			
						c.updateCustomerDiv = true;				
						c.getCustomerDiv = false;				
						c.getAllCustomersDiv = false;			
						c.customerFields = {
								"id" : null,
								"password" : ""
						};
						c.updateCustomerExceptionDiv = false;	
						c.updateCustomerTableDiv = false;		
					}
					
					c.updateCustomerResetButton = function() {
						c.customerFields = {
								"id" : null,
								"password" : ""
						};
						c.updateCustomerExceptionDiv = false;	
						c.updateCustomerTableDiv = false;		
					}

					c.getCustomerButton = function() {
						c.createCompanyDiv = false;				
						c.removeCompanyDiv = false;				
						c.updateCompanyDiv = false;				
						c.getCompanyDiv = false;				
						c.getAllCompaniesDiv = false;			
						c.createCustomerDiv = false;			
						c.removeCustomerDiv = false;			
						c.updateCustomerDiv = false;			
						c.getCustomerDiv = true;				
						c.getAllCustomersDiv = false;			
						c.customerFields = {
								"id" : null
						};
						c.getCustomerTableDiv = false;		
						c.getCustomerExceptionDiv = false;	
					}
					
					c.getCustomerResetButton = function() {
						c.customerFields = {
								"id" : null
						};
						c.getCustomerTableDiv = false;		
						c.getCustomerExceptionDiv = false;	
					}

					c.getAllCustomersButton = function() {
						c.createCompanyDiv = false;				
						c.removeCompanyDiv = false;				
						c.updateCompanyDiv = false;				
						c.getCompanyDiv = false;				
						c.getAllCompaniesDiv = false;			
						c.createCustomerDiv = false;			
						c.removeCustomerDiv = false;			
						c.updateCustomerDiv = false;			
						c.getCustomerDiv = false;				
						c.getAllCustomersDiv = true;			
						c.getAllCustomersTableDiv = false;		
						c.getAllCustomersExceptionDiv = false;	
					}

					c.createCouponButton = function() {
						c.createCouponDiv = true;				
						c.removeCouponDiv = false;				
						c.updateCouponDiv = false;				
						c.getThisCompanyDiv = false;			
						c.getCouponByIdDiv = false;				
						c.getAllCouponsDiv = false;				
						c.getCouponByTypeDiv = false;			
						c.getCouponByPriceDiv = false;			
						c.getCouponByDateDiv = false;			
						c.couponFields = {
								"id" : null,
								"title" : "",
								"startDate" : "",
								"endDate" : "",
								"amount" : "",
								"type" : "",
								"message" : "",
								"price" : "",
								"image" : ""
						};
						c.createCouponTableDiv = false;		
						c.createCouponExceptionDiv = false;	
					}
					
					c.createCouponResetButton = function() {
						c.couponFields = {
								"id" : null,
								"title" : "",
								"startDate" : "",
								"endDate" : "",
								"amount" : "",
								"type" : "",
								"message" : "",
								"price" : "",
								"image" : ""
						};
						c.createCouponTableDiv = false;		
						c.createCouponExceptionDiv = false;	
					}

					c.removeCouponButton = function() {
						c.createCouponDiv = false;		
						c.removeCouponDiv = true;		
						c.updateCouponDiv = false;		
						c.getThisCompanyDiv = false;	
						c.getCouponByIdDiv = false;		
						c.getAllCouponsDiv = false;		
						c.getCouponByTypeDiv = false;	
						c.getCouponByPriceDiv = false;	
						c.getCouponByDateDiv = false;	
						c.couponFields = {
								"id" : null
						};
						c.removeCouponExceptionDiv = false;			
						c.removeCouponSuccessDiv = false;			
						c.getAllCompanyCouponsTableDiv = false;		
						c.getAllCompanyCouponsExceptionDiv = false;	
					}

					c.updateCouponButton = function() {
						c.createCouponDiv = false;			
						c.removeCouponDiv = false;			
						c.updateCouponDiv = true;			
						c.getThisCompanyDiv = false;		
						c.getCouponByIdDiv = false;			
						c.getAllCouponsDiv = false;			
						c.getCouponByTypeDiv = false;		
						c.getCouponByPriceDiv = false;		
						c.getCouponByDateDiv = false;		
						c.couponFields = {
								"id" : null,
								"endDate" : "",
								"price" : "",
						};
						c.updateCouponTableDiv = false;		
						c.updateCouponExceptionDiv = false;	
					}
					
					c.updateCouponResetButton = function() {
						c.couponFields = {
								"id" : null,
								"endDate" : "",
								"price" : "",
						};
						c.updateCouponTableDiv = false;		
						c.updateCouponExceptionDiv = false;	
					}

					c.getThisCompanyButton = function() {
						c.createCouponDiv = false;			
						c.removeCouponDiv = false;			
						c.updateCouponDiv = false;			
						c.getThisCompanyDiv = true;			
						c.getCouponByIdDiv = false;			
						c.getAllCouponsDiv = false;			
						c.getCouponByTypeDiv = false;		
						c.getCouponByPriceDiv = false;		
						c.getCouponByDateDiv = false;		
						c.getMyCompanyTableDiv = false;		
						c.getMyCompanyExceptionDiv = false	
					}

					c.getCouponButton = function() {
						c.createCouponDiv = false;		
						c.removeCouponDiv = false;		
						c.updateCouponDiv = false;		
						c.getThisCompanyDiv = false;	
						c.getCouponByIdDiv = true;		
						c.getAllCouponsDiv = false;		
						c.getCouponByTypeDiv = false;	
						c.getCouponByPriceDiv = false;	
						c.getCouponByDateDiv = false;	
						c.couponFields = {
								"id" : null
						};
						c.getCouponByIdTableDiv = false;		
						c.getCouponByIdExceptionDiv = false;	
					}
					
					c.getCouponByIdResetButton = function() {
						c.couponFields = {
								"id" : null
						};
						c.getCouponByIdTableDiv = false;		
						c.getCouponByIdExceptionDiv = false;	
					}

					c.getAllCompanyCouponsButton = function() {
						c.createCouponDiv = false;		
						c.removeCouponDiv = false;		
						c.updateCouponDiv = false;		
						c.getThisCompanyDiv = false;	
						c.getCouponByIdDiv = false;		
						c.getAllCouponsDiv = true;		
						c.getCouponByTypeDiv = false;	
						c.getCouponByPriceDiv = false;	
						c.getCouponByDateDiv = false;	
						
						c.getAllCouponsTableDiv = false;		
						c.getAllCouponsExceptionDiv = false;	
					}

					c.getCouponsByTypeButton = function() {
						c.createCouponDiv = false;		
						c.removeCouponDiv = false;		
						c.updateCouponDiv = false;		
						c.getThisCompanyDiv = false;	
						c.getCouponByIdDiv = false;		
						c.getAllCouponsDiv = false;		
						c.getCouponByTypeDiv = true;	
						c.getCouponByPriceDiv = false;	
						c.getCouponByDateDiv = false;	
						c.couponFields = {
								"type" : ""
						};
						c.couponByTypeTableDiv = false;		
						c.couponByTypeExceptionDiv = false;	
					}
					
					c.getCouponByTypeResetButton = function() {
						c.couponFields = {
								"type" : ""
						};
						c.couponByTypeTableDiv = false;		
						c.couponByTypeExceptionDiv = false;	
					}

					c.getCouponsByPriceButton = function() {
						c.createCouponDiv = false;		
						c.removeCouponDiv = false;		
						c.updateCouponDiv = false;		
						c.getThisCompanyDiv = false;	
						c.getCouponByIdDiv = false;		
						c.getAllCouponsDiv = false;		
						c.getCouponByTypeDiv = false;	
						c.getCouponByPriceDiv = true;	
						c.getCouponByDateDiv = false;	
						c.couponFields = {
								"price" : ""
						};
						c.couponByPriceTableDiv = false;		
						c.couponByPriceExceptionDiv = false;	
					}
					
					c.getCouponByPriceResetButton = function() {
						c.couponFields = {
								"price" : ""
						};
						c.couponByPriceTableDiv = false;		
						c.couponByPriceExceptionDiv = false;	
					}

					c.getCouponsByDateButton = function() {
						c.createCouponDiv = false;		
						c.removeCouponDiv = false;		
						c.updateCouponDiv = false;		
						c.getThisCompanyDiv = false;	
						c.getCouponByIdDiv = false;		
						c.getAllCouponsDiv = false;		
						c.getCouponByTypeDiv = false;	
						c.getCouponByPriceDiv = false;	
						c.getCouponByDateDiv = true;	
						c.couponFields = {
								"endDate" : ""
						};
						
						c.couponByDateTableDiv = false;		
						c.couponByDateExceptionDiv = false;	
					}
					
					c.getCouponByDateResetButton = function() {
						c.couponFields = {
								"endDate" : ""
						};
						
						c.couponByDateTableDiv = false;		
						c.couponByDateExceptionDiv = false;	
					}

					c.purchaseCouponButton = function() {
						c.purchaseCouponDiv = true;						
						c.getAllPurchasedCouponsDiv = false;			
						c.getAllPurchasedCouponsByTypeDiv = false;		
						c.getAllPurchasedCouponsByPriceDiv = false;		
						
						c.getAllSystemCouponsTableDiv = false;		
						c.purchaseCouponSuccessDiv = false;			
						c.getAllSystemCouponExceptionDiv = false;	
					}

					c.getAllPurchasedCouponsButton = function() {
						c.purchaseCouponDiv = false;					
						c.getAllPurchasedCouponsDiv = true;				
						c.getAllPurchasedCouponsByTypeDiv = false;		
						c.getAllPurchasedCouponsByPriceDiv = false;		
						
						c.getAllPurchasedCouponsTableDiv = false;		
						c.getAllPurchasedCouponsExceptionDiv = false;	
					}

					c.getAllPurchasedCouponsByTypeButton = function() {
						c.purchaseCouponDiv = false;					
						c.getAllPurchasedCouponsDiv = false;			
						c.getAllPurchasedCouponsByTypeDiv = true;		
						c.getAllPurchasedCouponsByPriceDiv = false;		
						c.couponFields = {
								"type" : ""
						};
						c.purchasedCouponByTypeTableDiv = false;		
						c.purchasedCouponByTypeExceptionDiv = false;	
						
					}
					
					c.purchasedCouponByTypeResetButton = function() {
						c.couponFields = {
								"type" : ""
						};
						c.purchasedCouponByTypeTableDiv = false;		
						c.purchasedCouponByTypeExceptionDiv = false;	
					}

					c.getAllPurchasedCouponsByPriceButton = function() {
						c.purchaseCouponDiv = false;					
						c.getAllPurchasedCouponsDiv = false;			
						c.getAllPurchasedCouponsByTypeDiv = false;		
						c.getAllPurchasedCouponsByPriceDiv = true;		
						c.couponFields = {
								"price" : ""
						};
						c.purchasedCouponByPriceExceptionDiv = false;	
						c.purchasedCouponByPriceTableDiv = false;		
						
					}
					
					c.purchasedCouponByPriceResetButton = function() {
						c.couponFields = {
								"price" : ""
						};
						c.purchasedCouponByPriceExceptionDiv = false;	
						c.purchasedCouponByPriceTableDiv = false;		
					}
					
				});