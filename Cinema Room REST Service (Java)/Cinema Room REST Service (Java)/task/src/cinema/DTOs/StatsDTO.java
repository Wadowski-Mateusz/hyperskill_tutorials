package cinema.DTOs;

public record StatsDTO (
        int current_income,
        int number_of_available_seats,
        int number_of_purchased_tickets) {

}
