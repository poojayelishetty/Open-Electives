function validateForm() {
	  var n = parseInt(document.getElementById("count").value);
	  var sum1=n*(n+1)/2;
	  var sum2=parseInt("0");
	  var i=0,x;
	  var sample='a';
	  while(i<n)
		  {
		  x = parseInt(document.getElementById(sample).value);
		  sample=String.fromCharCode(sample.charCodeAt(0) + 1) 
		  sum2+=x;
		  i++;
		  }
	  if(sum1!=sum2)
		  {
		  alert(sum2);
		  return false;
		  }
	}