package ParceCsv;

import lombok.*;

@Getter
@AllArgsConstructor
@ToString
public class DatesStationsOpened {
    @NonNull
    private String nameOfStation;
    private String dateStationOpened;
}
