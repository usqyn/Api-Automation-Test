package account;

import com.fasterxml.jackson.databind.ObjectMapper;
import functionsfortest.NumberToStringConverter;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import com.fasterxml.jackson.core.type.TypeReference;



@RunWith(Parameterized.class)
public class Demo0002_DataDrivenTest {
    int input;
    String expected;
    //constructor
    public Demo0002_DataDrivenTest(int input, String expected){
        this.input = input;
        this.expected = expected;
    }
    @Test
    public void test(){
        NumberToStringConverter numberToStringConverter = new NumberToStringConverter();
        Assert.assertEquals(expected,numberToStringConverter.convert(input));
    }
    @Parameterized.Parameters
    public static Collection<Object[]> getData() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<TestData> testDataList = mapper.readValue(new File("src/main/resources/TestData/dataDriven.json"),
                new TypeReference<List<TestData>>() {});
        return testDataList.stream().map(testData ->new Object[]{testData.getInput(),testData.getExpected()}).toList();
    }

    static class TestData {
        private int input;  // Make fields private
        private String expected;

        // Default constructor (required by Jackson)
        public TestData() {
        }

        // Constructor for convenience (optional)
        public TestData(int input, String expected) {
            this.input = input;
            this.expected = expected;
        }

        // Getter for input
        public int getInput() {
            return input;
        }

        // Setter for input
        public void setInput(int input) {
            this.input = input;
        }

        // Getter for expected
        public String getExpected() {
            return expected;
        }

        // Setter for expected
        public void setExpected(String expected) {
            this.expected = expected;
        }
    }

}
