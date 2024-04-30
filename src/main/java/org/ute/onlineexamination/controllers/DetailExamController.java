package org.ute.onlineexamination.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import org.apache.poi.wp.usermodel.HeaderFooterType;
import org.apache.poi.xwpf.usermodel.*;
import org.ute.onlineexamination.daos.ExamDAO;
import org.ute.onlineexamination.daos.ExamQuestionDAO;
import org.ute.onlineexamination.daos.TakeExamDAO;
import org.ute.onlineexamination.models.Answer;
import org.ute.onlineexamination.models.ExamQuestion;
import org.ute.onlineexamination.models.Examination;
import org.ute.onlineexamination.utils.AppUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DetailExamController implements Initializable {
    public Label totalQuestion;
    public Label totalMinutes;
    public Label name;
    public Label course;
    public Label start;
    public Label end;
    public Label retryTimes;
    public Label scoringType;
    public AnchorPane scoreChartPane;
    public AnchorPane passChartPane;
    Examination examination;
    ExamDAO examDAO;
    TakeExamDAO takeExamDAO;
    ExamQuestionDAO examQuestionDAO;
    ObservableList<ExamQuestion> examQuestions;

    LineChart<Number,Number> scoreChart;
    PieChart passChart;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        examDAO = new ExamDAO();
        takeExamDAO = new TakeExamDAO();
        examQuestionDAO = new ExamQuestionDAO();
        examQuestions = FXCollections.observableArrayList();

        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Score");
        scoreChart = new LineChart<Number,Number>(xAxis,yAxis);
//        scoreChart.setPrefSize( scoreChartPane.getWidth(), scoreChartPane.getHeight());
        scoreChart.setTitle("Examination Performance");

        passChart = new PieChart();
        passChart.setTitle("Pass Percentage");
//        passChart.setPrefSize(passChartPane.getWidth(), passChartPane.getHeight());


        loadData();
        loadView();
    }


    public DetailExamController(Examination examination){
        this.examination = examination;
    }

    void loadData(){
        examQuestions = examQuestionDAO.getByExamId(examination.getId());
        ObservableList<Double> testPerformances = takeExamDAO.getScoreByExamId(examination.getId());
        // Score chart
        XYChart.Series scoreSeries = new XYChart.Series();
        for (int i = 0; i < testPerformances.size(); i++) {
            System.out.println(testPerformances.get(i));
            scoreSeries.getData().add(new XYChart.Data(i,testPerformances.get(i)));
        }
        scoreChart.getData().setAll(scoreSeries);
        // Passchart
        Double percentage = takeExamDAO.getPassPercentageByExamId(examination.getId());
        ObservableList<PieChart.Data> passChartData = FXCollections.observableArrayList(
                        new PieChart.Data("Pass", percentage),
                        new PieChart.Data("Fail", 100-percentage));

        passChart.setData(passChartData);

    }

    void loadView(){
        name.setText(examination.getName());
        course.setText(examination.course.getName());
        retryTimes.setText(String.valueOf(examination.getTime_retry()));
        scoringType.setText(examination.getScoring_type()==1 ? "Average": "Highest");
        start.setText(AppUtils.formatTime(examination.getStart()));
        end.setText(AppUtils.formatTime(examination.getEnd()));
        totalMinutes.setText(String.valueOf(examination.getTotal_minutes()));
        totalQuestion.setText(String.valueOf(examQuestions.size()));
        scoreChartPane.getChildren().add(scoreChart);
        passChartPane.getChildren().add(passChart);
    }
    public void exportFinalScore(ActionEvent even){
//            AppUtils.exportTable();

    }

    public void exportExamToDocs(ActionEvent actionEvent){
        String fileName = examination.getName() + ".docx";
        try (XWPFDocument doc = new XWPFDocument()) {
            // Create title
            XWPFParagraph title = doc.createParagraph();
            title.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun titleRun = title.createRun();
            titleRun.setBold(true);
            titleRun.setFontSize(16);
            titleRun.setText(examination.getName());

            // Create description
            XWPFParagraph desc = doc.createParagraph();
            desc.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun descRun = desc.createRun();
            descRun.setText("Description: "+ examination.getDescription());


            XWPFTable table = doc.createTable();
            table.setWidth("100%");
            // Header row
            XWPFTableRow headerRow = table.getRow(0);
            XWPFTableCell headerCell1 = headerRow.getCell(0);
            XWPFTableCell headerCell2 = headerRow.addNewTableCell();
            XWPFTableCell headerCell3 = headerRow.addNewTableCell();
            XWPFTableCell headerCell4 = headerRow.addNewTableCell();

            // Set text and make it bold for each header cell
            setBoldText(headerCell4, "Duration");
            headerCell1.setText("Course");
            headerCell2.setText("Start");
            headerCell3.setText("End");
            setBoldText(headerCell1);
            setBoldText(headerCell2);
            setBoldText(headerCell3);
            setBoldText(headerCell4);

            // Data row 1
            XWPFTableRow dataRow1 = table.createRow();
            dataRow1.getCell(0).setText(examination.course.getName());
            dataRow1.getCell(3).setText(String.valueOf(examination.getTotal_minutes()));
            dataRow1.getCell(1).setText(AppUtils.formatTime(examination.getStart()));
            dataRow1.getCell(2).setText(AppUtils.formatTime(examination.getEnd()));


            // Create exam questions
            for (int i = 0; i < examQuestions.size(); i++) {
                // Create question number
                XWPFParagraph question = doc.createParagraph();
                question.setAlignment(ParagraphAlignment.LEFT);
                XWPFRun questionRun = question.createRun();
                questionRun.setBold(true);
                questionRun.setFontSize(12);
                questionRun.setText("Question " + (i + 1) + ": " + examQuestions.get(i).question.getContent());
                // Create answers
                List<Answer> answers = examQuestions.get(i).question.getAnswers();
                char option = 'A';
                for (int j = 0; j < answers.size(); j++) {
                    XWPFParagraph answer = doc.createParagraph();
                    answer.setAlignment(ParagraphAlignment.LEFT);
                    XWPFRun answerRun = answer.createRun();
                    answerRun.setText("  " + option + ") " + answers.get(j).getContent());
                    option++;
                }
            }

            // Save document to .docx file
            try (FileOutputStream out = new FileOutputStream(fileName)) {
                doc.write(out);
            }

            System.out.println("Exam document created successfully!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void setBoldText(XWPFTableCell cell, String text) {
        cell.setText(text);
        cell.getParagraphArray(0).getRuns().get(0).setBold(true);
    }
    private void setBoldText(XWPFTableCell cell) {
        cell.getParagraphArray(0).getRuns().get(0).setBold(true);
    }
}
