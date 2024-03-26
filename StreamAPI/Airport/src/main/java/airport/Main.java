package airport;

import com.skillbox.airport.Aircraft;
import com.skillbox.airport.Airport;
import com.skillbox.airport.Flight;
import com.skillbox.airport.Terminal;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Airport airport = Airport.getInstance();

        System.out.println("Количество самолетов модели Airbus: " +
                findCountAircraftWithModelAirbus(airport, "Airbus"));
        System.out.println("Количество припаркованных самолетов по терминалам: " +
                findMapCountParkedAircraftByTerminalName(airport));
        System.out.println("Рейсы, вылетающие в ближайшие 3 часа: " +
                findFlightsLeavingInTheNextHours(airport, 3));
        System.out.println("Первый прилетающий рейс в терминал A: " +
                findFirstFlightArriveToTerminal(airport, "A"));
    }

    public static long findCountAircraftWithModelAirbus(Airport airport, String model) {
        List<Aircraft> allAircraft = airport.getAllAircrafts();

        return allAircraft.stream()
                .filter(aircraft -> aircraft.getModel().startsWith(model))
                .count();
    }

    public static Map<String, Integer> findMapCountParkedAircraftByTerminalName(Airport airport) {
        return airport.getTerminals().stream()
                .collect(Collectors.toMap(
                        Terminal::getName,
                        terminal -> terminal.getParkedAircrafts().size()
                ));
    }

    public static List<Flight> findFlightsLeavingInTheNextHours(Airport airport, int hours) {
        Instant now = Instant.now();
        Instant afterHours = now.plusSeconds(hours * 3600L);

        return airport.getTerminals().stream()
                .flatMap(terminal -> terminal.getFlights().stream())
                .filter(flight -> flight.getType() == Flight.Type.DEPARTURE)
                .filter(flight -> flight.getDate().isAfter(now) && flight.getDate().isBefore(afterHours))
                .collect(Collectors.toList());
    }

    public static Optional<Flight> findFirstFlightArriveToTerminal(Airport airport, String terminalName) {
        Instant now = Instant.now();

        return airport.getTerminals().stream()
                .filter(terminal -> terminal.getName().equals(terminalName))
                .flatMap(terminal -> terminal.getFlights().stream())
                .filter(flight -> flight.getType() == Flight.Type.ARRIVAL)
                .filter(flight -> flight.getDate().isAfter(now))
                .min(Comparator.comparing(Flight::getDate));
    }
}