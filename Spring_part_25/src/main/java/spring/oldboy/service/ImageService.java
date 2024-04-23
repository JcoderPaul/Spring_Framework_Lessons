package spring.oldboy.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

/* Стандартная аннотация для Service bean-ов */
@Service
/* Внедрение bucket-a пойдет через конструктор */
@RequiredArgsConstructor
public class ImageService {

    /*
    Как мы уже решили - все картинки будем хранить в корне проекта,
    создадим под это папку images и сошлем на нее через параметры
    аннотации наш bucket.
    */
    @Value("${app.image.bucket:E:\\Spring_Lessons\\Spring_part_19\\images}")
    private final String bucket;

    /*
    Метод загрузки изображения получает с аргументами путь к файлу картинки,
    а также наиболее универсальный вариант получения данных это InputStream.
    Аннотацией пробрасываем исключения в Runtime-e.
    */
    @SneakyThrows
    public void uploadAvatar(String imagePath, InputStream content) {
        Path fullImagePath = Path.of(bucket, imagePath);

        /* Применим try-with-resources чтобы автоматически закрыть InputStream */
        try (content) {
            /* Создаем директорию, на случай если ее нет */
            Files.createDirectories(fullImagePath.getParent());
            /*
            Пишем в нее картинку, и т.к. это картинка (можем к примеру, заранее
            наложить ограничения на размер), то мы можем сразу считать ее всю,
            в отличие от видео, когда существует риск положить приложение с
            перегрузкой памяти.
            */
            Files.write(fullImagePath, content.readAllBytes(), CREATE, TRUNCATE_EXISTING);
        }
    }

    /* Пробрасываем исключение */
    @SneakyThrows
    /*
    Метод условно-универсальный, картинки может у user-a и не быть,
    по этому Optional, поскольку мы пока учимся и картинки у нас
    маленькие, обертывать обциональю мы будем массив - byte[], в
    идеале это должен быть InputStream, который грузится частями.

    */
    public Optional<byte[]> getAvatar(String imagePath) {
        Path fullImagePath = Path.of(bucket, imagePath);
        /*
        Проверяем, есть ли картинка по указанному пути, если есть, то
        читаем все байты из указанного пути, если нет, возвращаем пустой
        объект.
        */
        return Files.exists(fullImagePath)
                ? Optional.of(Files.readAllBytes(fullImagePath))
                : Optional.empty();
    }
}
