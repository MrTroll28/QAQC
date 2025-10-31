package me.kn.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class ParkingTests extends BaseTestGrid {

    @Test
    @DisplayName("Motorbike parking for 1 hour → 5.000đ")
    public void testMotorValidFeeNoExtras() {
        // 1h → 5.000đ
        LocalDateTime start = LocalDateTime.of(2025, 1, 1, 10, 0);
        LocalDateTime end   = LocalDateTime.of(2025, 1, 1, 11, 0);

        parkingPage.setPlate("59A-111.11");
        parkingPage.selectVehicleByValue("motor");
        parkingPage.setStart(start);
        parkingPage.setEnd(end);
        parkingPage.setOvernight(false);
        parkingPage.setMember(false);

        parkingPage.clickCalculate();
        parkingPage.waitForFeeToAppear();

        assertThat(parkingPage.getTotalFee()).isEqualTo("5.000đ");
    }


    @Test
    @DisplayName("Car parking exceeds daily cap → 120.000đ")
    public void testCarCapFee() {
        // 10h → 200k nhưng cap = 120k
        LocalDateTime start = LocalDateTime.of(2025, 1, 1, 0, 0);
        LocalDateTime end   = LocalDateTime.of(2025, 1, 1, 10, 0);

        parkingPage.setPlate("51H-222.22");
        parkingPage.selectVehicleByValue("car");
        parkingPage.setStart(start);
        parkingPage.setEnd(end);
        parkingPage.setOvernight(false);
        parkingPage.setMember(false);

        parkingPage.clickCalculate();
        parkingPage.waitForFeeToAppear();

        assertThat(parkingPage.getTotalFee()).isEqualTo("120.000đ");
    }


    @Test
    @DisplayName("Truck member discount → 4h = 120.000 -10% = 108.000đ")
    public void testTruckMemberDiscount() {
        // 4h → 120.000 → member -10% → 108.000đ
        LocalDateTime start = LocalDateTime.of(2025, 1, 1, 8, 0);
        LocalDateTime end   = LocalDateTime.of(2025, 1, 1, 12, 0);

        parkingPage.setPlate("60C-333.33");
        parkingPage.selectVehicleByValue("truck");
        parkingPage.setStart(start);
        parkingPage.setEnd(end);
        parkingPage.setOvernight(false);
        parkingPage.setMember(true);

        parkingPage.clickCalculate();
        parkingPage.waitForFeeToAppear();

        assertThat(parkingPage.getTotalFee()).isEqualTo("108.000đ");
    }


    @Test
    @DisplayName("All fields empty → validation errors")
    public void testParkingInvalidAllFields() {

        parkingPage.clickClear();
        parkingPage.clickCalculate();

        String message = parkingPage.getParkingMessage();

        assertThat(message).contains("Vui lòng nhập biển số xe");
        assertThat(message).contains("Vui lòng chọn loại xe");
        assertThat(message).contains("Vui lòng nhập giờ vào");
        assertThat(message).contains("Vui lòng nhập giờ ra");

        assertThat(parkingPage.isInvalid("plate")).isTrue();
        assertThat(parkingPage.isInvalid("vehicle")).isTrue();
        assertThat(parkingPage.isInvalid("start")).isTrue();
        assertThat(parkingPage.isInvalid("end")).isTrue();
    }


    @Test
    @DisplayName("End time before start → error & end invalid")
    public void testParkingEndBeforeStart() {

        LocalDateTime start = LocalDateTime.of(2025, 1, 1, 10, 0);
        LocalDateTime end   = LocalDateTime.of(2025, 1, 1, 9, 50);

        parkingPage.setPlate("59X-444.44");
        parkingPage.selectVehicleByValue("motor");
        parkingPage.setStart(start);
        parkingPage.setEnd(end);

        parkingPage.clickCalculate();

        assertThat(parkingPage.getParkingMessage()).contains("Giờ ra phải sau giờ vào");
        assertThat(parkingPage.isInvalid("end")).isTrue();
    }


    @Test
    @DisplayName("Overnight fee applied → base + 30.000đ = 150.000đ")
    public void testParkingOvernightFeeApplied() {

        // base = cap 120.000 + overnight 30.000 = 150.000đ
        LocalDateTime start = LocalDateTime.of(2025, 1, 1, 20, 0);
        LocalDateTime end   = LocalDateTime.of(2025, 1, 2, 2, 0);

        parkingPage.setPlate("59Z-555.55");
        parkingPage.selectVehicleByValue("car");
        parkingPage.setStart(start);
        parkingPage.setEnd(end);
        parkingPage.setOvernight(true);
        parkingPage.setMember(false);

        parkingPage.clickCalculate();
        parkingPage.waitForFeeToAppear();

        assertThat(parkingPage.getTotalFee()).isEqualTo("150.000đ");
    }

}
