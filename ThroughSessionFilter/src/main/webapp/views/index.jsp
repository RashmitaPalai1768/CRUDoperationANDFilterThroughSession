<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
	integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/@fortawesome/fontawesome-free@5.15.4/css/fontawesome.min.css">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css">


<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">

<script type="text/javascript"
	src="https://code.jquery.com/jquery-1.12.4.js"></script>

<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<link rel="stylesheet"
	href="https://cdn.datatables.net/1.10.22/css/jquery.dataTables.min.css" />

<!-- jQuery library file -->
<script type="text/javascript"
	src="https://code.jquery.com/jquery-3.5.1.js">
	
</script>

<!-- Datatable plugin JS library file -->
<script type="text/javascript"
	src="https://cdn.datatables.net/1.10.22/js/jquery.dataTables.min.js">
	
</script>
</head>
<body>
<center>
			<form action="save" method="post">
	Name:<input type="text" name="name1" id="name1"><br><br>
	Salary:<input type="number" name="salary1" id="salary1"><br><br>
	<input type="submit" value="submit">
	
</form>
<button onclick="return search();">search</button>
<br>
<label for="txtName"> Export To Excel:</label>
	<a title="Excel" id="anchExcel" runat="server"
		class="btn btn-inverse btn-lg" onclick="generateExcelsheet()"><i
		class="fa fa-file-excel-o" style="color: #54ff00;"></i></a>
<table border="2">
	<tr><td>Sl#</td>
	<td>Name</td>
	<td>Salary</td></tr>
	<c:forEach items="${ss}" var="ab" varStatus="vv">
	<tr>
	
		<td>${vv.count}</td>
		<td>${ab.name}</td>
		<td>${ab.salary}</td>
		</tr>
		</c:forEach>
	
</table>
</center>
<form action="ExcelReport.htm" id="excelsheetReportForm" method="POST">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
	</form>
	
	<form action="SearchExcelReport.htm" id="excelsheetReportForm1" method="POST">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
			<input type="hidden" name="name" id="name">
			<input type="hidden" name="salary" id="salary">
	</form>
<script type="text/javascript">
	function generateExcelsheet(){
		alert("fghj");
		$("#excelsheetReportForm").submit();		
		}

	function search(){
		alert("tyhuj");
		var nn=$("#name1").val();
		var ss=$("#salary1").val();
		$("#name").val(nn);
		$("#salary").val(ss);
		$("#excelsheetReportForm1").submit();
		}
</script>
</body>
</html>