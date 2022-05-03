
	
	function showCustomAlert(M,C){

		if(M=='S'){
			$(document).Toasts('create', {
		        class: 'bg-success',
		        title: 'Success',
		        icon: 'fas fa-envelope fa-lg',
		        subtitle: 'Subtitle',
		        autohide: true,
		        delay: 3000,
		        body: C
		      })
			
		}else if(M=='D'){
			$(document).Toasts('create', {
		        class: 'bg-danger',
		        title: 'Error',
		        icon: 'fas fa fa-exclamation-circle fa-lg',
		        subtitle: '',
		        autohide: true,
		        delay: 3000,
		        body: C
		      })
			
			
		}else if(M=='W'){
			$(document).Toasts('create', {
		        class: 'bg-warning',
		        title: 'Warning',
		        icon: 'fas fa-envelope fa-lg',
		        subtitle: 'Subtitle',
		        autohide: true,
		        delay: 3000,
		        body: C
		      })
			
			
		}else if(M=='I'){
			
			$(document).Toasts('create', {
		        class: 'bg-info',
		        title: 'Info',
		        icon: 'fas fa-envelope fa-lg',
		        subtitle: 'Subtitle',
		        autohide: true,
		        delay: 3000,
		        body: C
		      })
	
		}
	
	}
	
	
	 function getDate(dateObject) {
	        var d = new Date(dateObject);
	        var day = d.getDate();
	        var month = d.getMonth() + 1;
	        var year = d.getFullYear();
	        if (day < 10) {
	            day = "0" + day;
	        }
	        if (month < 10) {
	            month = "0" + month;
	        }
	        var date = day + "/" + month + "/" + year;

	        return date;
	    }
	 
	 
	 function customSuccessMsg(M){
		 
		 if(M=='S'){
			 $.confirm({
		         	type: 'green',
		             title: 'Confirm',
		             content: 'Save Successfully!!',
		             buttons: {
		                 ok: function () {
		                   
		                 },
		                 cancel: function () {

		                 }
		             }
		         });
			 
		 }else if(M=='W'){
			 $.confirm({
		         	type: 'orange',
		             title: 'Confirm',
		             content: 'Are your sure?',
		             buttons: {
		                 ok: function () {
		                   
		                 },
		                 cancel: function () {

		                 }
		             }
		         });
			 
		 }else if(M=='F'){
			 $.confirm({
		         	type: 'red',
		             title: 'Confirm',
		             content: 'Are your sure?',
		             buttons: {
		                 ok: function () {
		                   
		                 },
		                 cancel: function () {

		                 }
		             }
		         });
			 
		 }
		 
	 }
	
	
	

