import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Convert;

@Convert
public class CourseTypeConverter implements AttributeConverter<CoursesType, Integer> {


    @Override
    public Integer convertToDatabaseColumn(CoursesType type) {
        if (type == null) {
            return null;
        }
        switch (type) {
            case DESIGN:
                return 1;
            case BUSINESS:
                return 2;
            case PROGRAMMING:
                return 3;
            case MARKETING:
                return 4;
            case MANAGEMENT:
                return 5;
            default:
                throw new IllegalArgumentException(type + " не поддерживается.");
        }
    }

    @Override
    public CoursesType convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        switch (dbData) {
            case 1:
                return CoursesType.DESIGN;
            case 2:
                return CoursesType.BUSINESS;
            case 3:
                return CoursesType.PROGRAMMING;
            case 4:
                return CoursesType.MARKETING;
            case 5:
                return CoursesType.MANAGEMENT;
            default:
                throw new IllegalArgumentException(dbData + " не поддерживается.");
        }
    }
}