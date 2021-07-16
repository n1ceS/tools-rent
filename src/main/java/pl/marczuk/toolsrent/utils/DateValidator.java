package pl.marczuk.toolsrent.utils;

import pl.marczuk.toolsrent.annotation.DateValidation;
import pl.marczuk.toolsrent.dto.OrderDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DateValidator implements ConstraintValidator<DateValidation, Object> {
    private String message;
    @Override
    public void initialize(DateValidation constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        OrderDto orderDto = (OrderDto) obj;
        boolean isValid = orderDto.getStartDate().isBefore(orderDto.getEndDate());
        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context
                    .buildConstraintViolationWithTemplate(message)
                    .addPropertyNode("endDate")
                    .addConstraintViolation();
        }
        return isValid;
    }
}