module com.example.hellojavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.pdfbox;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;


    opens com.example.hellojavafx to javafx.fxml, org.apache.poi.ooxml, org.apache.poi.poi;
    exports com.example.hellojavafx;
}