<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>upload.jsp</title>
</head>
<body>
	<h3><%= request.getRequestURI() %></h3>
	<hr>

	<form action="/fileUpload/doit" method="post" enctype="multipart/form-data">
		
		<ul>
			<li>File Uploader: <input type="text" name="uploader" size="10" maxlength="10"></li>
			<li><input type="file" name="files"></li>
		</ul>

		<!-- <p>&nbsp;</p> -->

		<input type="submit" value="Upload">

	</form>
</body>
</html>