package cuik.utilities;

public final class Messages {
    private Messages() { // No queremos que la clase sea instanciada
    }

    // Auth / validation
    public static String UserDoesntExist = "El usuario no existe";
    public static String UserAlreadyExists = "El usuario ya existe";
    public static String InvalidLogin = "Datos de login invalidos";
    public static String PasswordIsRequired = "La contraseña es requerida";
    public static String TokenExpiredOrInvalid = "Token inválido o expirado";
    public static String InvalidToken = "Token inválido";
    public static String AuthenticationRequired = "Se requiere autenticación";
    public static String InsufficientPermissions = "Permisos insuficientes";
    public static String NotFound = "No encontrado";

    // Validation
    public static String ValidationRequired = "El campo %s es requerido";
    public static String ValidationBlank = "El campo %s no puede estar vacío";
    public static String ValidationMinLength = "El campo %s debe tener al menos %d caracteres";
    public static String ValidationMaxLength = "El campo %s no puede superar los %d caracteres";
    public static String ValidationInvalidUuid = "El campo %s debe ser un UUID válido";
    public static String ValidationInvalidEmail = "El campo %s debe ser un email válido";
    public static String ValidationMinValue = "El campo %s debe ser mayor o igual a %s";
    public static String ValidationMaxValue = "El campo %s debe ser menor o igual a %s";
    public static String ValidationPositive = "El campo %s debe ser mayor que 0";

    // Server / framework
    public static String ShouldHaveBeenJson = "Debería haber sido JSON?";
    public static String InvalidController = "%s no es un controlador válido";
    public static String PathCannotBeNull = "El argumento path no puede ser nulo";
    public static String HandlerCannotBeNull = "El argumento handler no puede ser nulo";

    // Data layer
    public static String TypeNeedsEmptyConstructor = "El tipo %s necesita un constructor público y sin argumentos";
    public static String ErrorMappingType = "Error al intentar mapear %s";
    public static String MappableModelNeedsConstructor = "El modelo mapeado necesita un constructor sin argumentos";
    public static String SqlException = "Excepción SQL: %s";
    public static String UnexpectedException = "Excepción inesperada: %s";
    public static String CouldNotCreateEntity = "No se pudo crear %s";
    public static String CannotUpdateWithoutId = "No se puede actualizar una entidad sin id";

    // Dep injector
    public static String FailedToInstantiate = "No se pudo instanciar %s";
    public static String FailedToInstantiateType = "No se pudo instanciar el tipo %s";
    public static String FailedToBuild = "No se pudo construir %s";
    public static String DependencyInjectionNeedsConstructor = "La inyección de dependencias necesita un constructor";
}
