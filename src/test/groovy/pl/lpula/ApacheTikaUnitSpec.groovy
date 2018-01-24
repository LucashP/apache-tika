package pl.lpula

import org.apache.tika.Tika
import org.apache.tika.io.TemporaryResources
import org.apache.tika.io.TikaInputStream
import org.apache.tika.metadata.Metadata
import org.springframework.mock.web.MockMultipartFile
import org.springframework.web.multipart.MultipartFile
import spock.lang.Specification

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

class ApacheTikaUnitSpec extends Specification {

    def "test with File class"() {
        given:
        ClassLoader classLoader = getClass().getClassLoader()
        File file = new File(classLoader.getResource(fileName).getFile())
        Tika tika = new Tika()

        when:
        def result = tika.detect(file)

        then:
        result == expected

        where:
        fileName                 || expected
        "test.png"               || "image/png"
        "test.js"                || "application/javascript"
        "fakeFileFromTestJs.png" || "application/javascript"
        "test.json"              || "application/json"
        "test.php"               || "text/x-php"
        "test.pptx"              || "application/vnd.openxmlformats-officedocument.presentationml.presentation"
        "test.xlsx"              || "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
    }

    def "test with InputStream #1"() {
        given:
        ClassLoader classLoader = getClass().getClassLoader()
        File file = new File(classLoader.getResource(fileName).getFile())
        Tika tika = new Tika()

        def metadata = new Metadata()
        Path path = Paths.get(file.getAbsolutePath())
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(Files.readAllBytes(path))
        def bufferedInputStream = new BufferedInputStream(byteArrayInputStream)
        def tikaInputStream = TikaInputStream.get(bufferedInputStream, new TemporaryResources())

        when:
        def result = tika.detect(tikaInputStream, metadata)

        then:
        println result
        result == expected

        where:
        fileName    || expected
        "test.js"   || "application/javascript"
        "test.json" || "application/json"
        "test.php"  || "text/x-php"
        "test.pptx" || "application/vnd.openxmlformats-officedocument.presentationml.presentation"
        "test.xlsx" || "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
    }

    def "test with InputStream #2"() {
        given:
        ClassLoader classLoader = getClass().getClassLoader()
        File file = new File(classLoader.getResource(fileName).getFile())
        Tika tika = new Tika()

        def metadata = new Metadata()
        InputStream inputStream = new BufferedInputStream(new FileInputStream(file))
        MultipartFile multipartFile = new MockMultipartFile(fileName, inputStream)
        def tikaInputStream = TikaInputStream.get(multipartFile.getBytes(), metadata)

        when:
        def result = tika.detect(tikaInputStream, metadata)

        then:
        result == expected

        where:
        fileName    || expected
        "test.js"   || "application/javascript"
        "test.json" || "application/json"
        "test.php"  || "text/x-php"
        "test.pptx" || "application/vnd.openxmlformats-officedocument.presentationml.presentation"
        "test.xlsx" || "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
    }

    def "test with InputStream #3"() {
        given:
        ClassLoader classLoader = getClass().getClassLoader()
        File file = new File(classLoader.getResource(fileName).getFile())
        Tika tika = new Tika()

        def metadata = new Metadata()
        Path path = Paths.get(file.getAbsolutePath())
        def tikaInputStream = TikaInputStream.get(path, metadata)

        when:
        def result = tika.detect(tikaInputStream, metadata)

        then:
        result == expected

        where:
        fileName    || expected
        "test.js"   || "application/javascript"
        "test.json" || "application/json"
        "test.php"  || "text/x-php"
        "test.pptx" || "application/vnd.openxmlformats-officedocument.presentationml.presentation"
        "test.xlsx" || "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
    }

    def "test with InputStream #4"() {
        given:
        ClassLoader classLoader = getClass().getClassLoader()
        File file = new File(classLoader.getResource(fileName).getFile())
        Tika tika = new Tika()

        def metadata = new Metadata()
        def tikaInputStream = TikaInputStream.get(file, metadata)

        when:
        def result = tika.detect(tikaInputStream, metadata)

        then:
        result == expected

        where:
        fileName    || expected
        "test.js"   || "application/javascript"
        "test.json" || "application/json"
        "test.php"  || "text/x-php"
        "test.pptx" || "application/vnd.openxmlformats-officedocument.presentationml.presentation"
        "test.xlsx" || "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
    }
}
