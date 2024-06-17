package example.cashcard;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import example.cashcard.model.CashCard;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContentAssert;

@JsonTest
public class CashCardJsonTest {
  @Autowired private JacksonTester<CashCard> json;

  @Test
  public void cashCardSerializationTest() throws IOException {
    CashCard cashCard = new CashCard(99L, 123.45);
    JsonContentAssert jsonContentAssert = assertThat(json.write(cashCard));
    jsonContentAssert.isStrictlyEqualToJson("expected.json");
    jsonContentAssert.hasJsonPathNumberValue("@.id");
    jsonContentAssert.extractingJsonPathNumberValue("@.id").isEqualTo(99);
    jsonContentAssert.hasJsonPathNumberValue("@.amount");
    jsonContentAssert.extractingJsonPathNumberValue("@.amount").isEqualTo(123.45);
  }

  @Test
  void cashCardDeserializationTest() throws IOException {
    String expected =
        """
           {
               "id":99,
               "amount":123.45
           }
           """;
    assertThat(json.parse(expected)).isEqualTo(new CashCard(99L, 123.45));
    assertThat(json.parseObject(expected).id()).isEqualTo(99);
    assertThat(json.parseObject(expected).amount()).isEqualTo(123.45);
  }
}
