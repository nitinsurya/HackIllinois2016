<jsp:useBean id="sample" scope="page" class="com.uic.edu.MostPrevalentLocation" /> // sample is java file name

String name = request.getParameter("text1");
int iRowAffected = 0;   

//-------now pass parameter "name" to your sample java file

sample.search("name");