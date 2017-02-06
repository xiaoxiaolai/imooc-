package entity;


import java.util.List;

/**
 *
 */
public class CourseListModel {


    @Override
    public String toString() {
        return "CourseListModel{" +
                "errorCode=" + errorCode +
                ", items=" + data +
                '}';
    }

    private int errorCode;

    private List<ItemsEntity> data;

    public void setErr(int err) {
        this.errorCode = err;
    }

    public void setItems(List<ItemsEntity> items) {
        this.data = items;
    }

    public int getErr() {
        return errorCode;
    }

    public List<ItemsEntity> getItems() {
        return data;
    }

    public static class ItemsEntity {
        @Override
        public String toString() {
            return "ItemsEntity{" +
                    "pic='" + pic + '\'' +
                    ", name='" + name + '\'' +
                    ", numbers=" + numbers +
                    ", finished=" + finished +
                    ", max_chapter_seq=" + max_chapter_seq +
                    ", max_media_seq=" + max_media_seq +
                    '}';
        }

        public String pic;
        public String name;
        public int numbers;
        public int finished;
        public int max_chapter_seq;
        public int max_media_seq;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int id;

        public String getPic() {
            return pic;
        }

        public String getName() {
            return name;
        }

        public int getFinished() {
            return finished;
        }

        public int getNumbers() {
            return numbers;
        }

        public int getMax_chapter_seq() {
            return max_chapter_seq;
        }

        public int getMax_media_seq() {
            return max_media_seq;
        }

        public void setPic(String pic) {

            this.pic = pic;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setNumbers(int numbers) {
            this.numbers = numbers;
        }

        public void setFinished(int finished) {
            this.finished = finished;
        }

        public void setMax_chapter_seq(int max_chapter_seq) {
            this.max_chapter_seq = max_chapter_seq;
        }

        public void setMax_media_seq(int max_media_seq) {
            this.max_media_seq = max_media_seq;
        }
    }
}