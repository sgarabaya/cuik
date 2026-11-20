package cuik.data.pojo;

import java.time.LocalDateTime;
import java.util.UUID;

import cuik.data.Column;

public class PurchaseView {
        @Column
        private UUID id;

        @Column
        private UUID userId;

        @Column
        private String userName;

        @Column
        private String userEmail;

        @Column
        private LocalDateTime created;

        @Column
        private double total;

        @Column
        private int items;

        public UUID getId() {
                return id;
        }

        public void setId(UUID id) {
                this.id = id;
        }

        public UUID getUserId() {
                return userId;
        }

        public void setUserId(UUID userId) {
                this.userId = userId;
        }

        public String getUserName() {
                return userName;
        }

        public void setUserName(String userName) {
                this.userName = userName;
        }

        public String getUserEmail() {
                return userEmail;
        }

        public void setUserEmail(String userEmail) {
                this.userEmail = userEmail;
        }

        public LocalDateTime getCreated() {
                return created;
        }

        public void setCreated(LocalDateTime created) {
                this.created = created;
        }

        public double getTotal() {
                return total;
        }

        public void setTotal(double total) {
                this.total = total;
        }

        public int getItems() {
                return items;
        }

        public void setItems(int items) {
                this.items = items;
        }
}
