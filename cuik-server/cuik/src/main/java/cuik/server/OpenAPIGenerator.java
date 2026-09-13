package cuik.server;

public class OpenAPIGenerator {
    // public static JSONObject generateOpenApi(Class<?> clazz) {
    // JSONObject paths = new JSONObject();
    // String basePath = clazz.isAnnotationPresent(Controller.class)
    // ? "/" + clazz.getAnnotation(Controller.class).value()
    // : "";

    // for (Method method : clazz.getDeclaredMethods()) {
    // String httpVerb = getHttpVerb(method);
    // if (httpVerb == null)
    // continue;

    // String fullPath = basePath;
    // if (method.isAnnotationPresent(Path.class)) {
    // fullPath += method.getAnnotation(Path.class).value();
    // }
    // fullPath = fullPath.replaceAll("/:([^/]+)", "/{$1}");

    // JSONObject operation = new JSONObject();
    // JSONArray parameters = new JSONArray();

    // for (Parameter param : method.getParameters()) {
    // String paramName = param.getName();
    // if (fullPath.contains("{" + paramName + "}")) {
    // parameters.put(new JSONObject()
    // .put("name", paramName)
    // .put("in", "path")
    // .put("required", true)
    // .put("schema", buildSchema(param.getType())));
    // } else if (httpVerb.equals("post") || httpVerb.equals("put")) {
    // operation.put("requestBody", new JSONObject()
    // .put("content", new JSONObject()
    // .put("application/json", new JSONObject()
    // .put("schema", buildSchema(param.getType())))));
    // } else {
    // parameters.put(new JSONObject()
    // .put("name", paramName)
    // .put("in", "query")
    // .put("schema", buildSchema(param.getType())));
    // }
    // }

    // if (!parameters.isEmpty()) {
    // operation.put("parameters", parameters);
    // }

    // Class<?> returnType = method.getReturnType();
    // if (returnType != void.class) {
    // JSONObject responseContent = new JSONObject()
    // .put("application/json", new JSONObject().put("schema",
    // buildSchema(returnType)));
    // operation.put("responses", new JSONObject().put("200", new JSONObject()
    // .put("description", "OK")
    // .put("content", responseContent)));
    // } else {
    // operation.put("responses",
    // new JSONObject().put("204", new JSONObject().put("description", "No
    // Content")));
    // }

    // if (!paths.has(fullPath)) {
    // paths.put(fullPath, new JSONObject());
    // }
    // paths.getJSONObject(fullPath).put(httpVerb, operation);
    // }

    // return new JSONObject()
    // .put("openapi", "3.0.0")
    // .put("info", new JSONObject().put("title", clazz.getSimpleName() + "
    // API").put("version", "1.0.0"))
    // .put("paths", paths);
    // }

    // private static JSONObject buildSchema(Class<?> type) {
    // JSONObject schema = new JSONObject();

    // if (type.isArray()) {
    // return schema.put("type", "array").put("items",
    // buildSchema(type.getComponentType()));
    // }

    // String typeName = type.getSimpleName().toLowerCase();
    // if (typeName.contains("string")) {
    // return schema.put("type", "string");
    // } else if (typeName.contains("int") || typeName.contains("long") ||
    // typeName.contains("short")) {
    // return schema.put("type", "integer");
    // } else if (typeName.contains("double") || typeName.contains("float")) {
    // return schema.put("type", "number");
    // } else if (typeName.contains("boolean")) {
    // return schema.put("type", "boolean");
    // }

    // schema.put("type", "object");
    // JSONObject properties = new JSONObject();
    // for (Field field : type.getDeclaredFields()) {
    // properties.put(field.getName(), buildSchema(field.getType()));
    // }
    // return schema.put("properties", properties);
    // }

    // private static String getHttpVerb(Method method) {
    // if (method.isAnnotationPresent(Get.class))
    // return "get";
    // if (method.isAnnotationPresent(Post.class))
    // return "post";
    // if (method.isAnnotationPresent(Put.class))
    // return "put";
    // if (method.isAnnotationPresent(Delete.class))
    // return "delete";
    // return null;
    // }
}
