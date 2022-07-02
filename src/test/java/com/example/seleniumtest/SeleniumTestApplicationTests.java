package com.example.seleniumtest;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class SeleniumTestApplicationTests {
    private static final String WEBDRIVER = "webdriver.chrome.driver";
    private static final String WEB_DRIVER_PATH = "C:/Users/biolk/Downloads/chromedriver.exe";
    private WebDriver driver;
    private ChromeOptions options;
    @BeforeEach
    public void setDriverSetting() {
        System.setProperty(WEBDRIVER, WEB_DRIVER_PATH);
        options = new ChromeOptions();
        options.setHeadless(true);
        options.addArguments("--lang=ko");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-gpu");
        options.setCapability("ignoreProtectedModeSettings", true);

        //페이지가 로드될 때까지 대기
        //Normal: 로드 이벤트 실행이 반환 될 때 까지 기다린다.
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
    }

    @Test
    @DisplayName("문제 출력")
    public void 문제_가져오기() {
        //세션 시작
        driver = new ChromeDriver(options);

        try {
            //문제 url
            String url = "https://programmers.co.kr/learn/courses/30/lessons/12969";
            driver.get(url);

            //문제 설명
            String questionDescription = driver.findElement(By.xpath("//*[@id=\"tour2\"]/div/div/p[1]")).getText();
            //제한 조건
            String limitation = driver.findElement(By.xpath("//*[@id=\"tour2\"]/div/div/ul")).getText();
            //예시 입력
            String inputValue = driver.findElement(By.xpath("//*[@id=\"tour2\"]/div/div/div[1]/pre/code")).getText();
            //출력
            String outputValue = driver.findElement(By.xpath("//*[@id=\"tour2\"]/div/div/div[2]/pre/code")).getText();

            System.out.println("questionDescription: " + questionDescription + "\n" +
                    "limitation: " + limitation + "\n" +
                    "inputValue: " + inputValue + "\n" +
                    "outputValue: " + outputValue);
        } finally {
            driver.close();
        }
    }

    @Test
    @DisplayName("기본 코드 템플릿")
    void getSolutions() {

        driver = new ChromeDriver(options);
        List<String> questions = new ArrayList<>();
        try {
            String[] languages = {"java", "javascript", "python3"}; //가져올 언어
            for (String language : languages) {

                //문제 url
                String url = "https://programmers.co.kr/learn/courses/30/lessons/12969?language=";
                driver.get(url + language);

                //코드 태그 목록 추출, 합치기
                StringBuilder buffer = new StringBuilder();
                List<WebElement> solutions = driver.findElements(By.className("CodeMirror-line")); //모든 코드 줄 가져오기

                for (WebElement solution : solutions) {
                    String eachLine = solution.findElement(By.tagName("span")).getText();
                    buffer.append(eachLine);

                }
                questions.add(buffer.toString());
            }
        } finally {
            driver.close();
        }
        System.out.println(questions);
    }
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    static class Question{
        private Long id;
        private String title;
        private String question;
        private String level;
    }

    static class Question_template{
        private Long id;
        private String language;
        private String template;
    }
}