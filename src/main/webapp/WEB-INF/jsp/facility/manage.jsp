<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <title>Pengurusan Fasiliti (Manager)</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="bg-light">
    <div class="container mt-5">
        <h2 class="mb-4">Urus Fasiliti E-Sukan</h2>
        
        <!-- Borang Tambah / Edit -->
        <div class="card mb-5 shadow-sm">
            <div class="card-header bg-primary text-white">
                <h5>${facilityToEdit != null ? "Kemaskini Fasiliti" : "Tambah Fasiliti Baru"}</h5>
            </div>
            <div class="card-body">
                <form action="${pageContext.request.contextPath}/manager/facility" method="POST">
                    <input type="hidden" name="id" value="${facilityToEdit.id}">
                    <div class="row mb-3">
                        <div class="col-md-6">
                            <label class="form-label">Nama Fasiliti</label>
                            <input type="text" name="name" class="form-control" value="${facilityToEdit.name}" required>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Jenis/Kategori</label>
                            <input type="text" name="type" class="form-control" value="${facilityToEdit.type}" placeholder="e.g. PC Room" required>
                        </div>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Deskripsi</label>
                        <textarea name="description" class="form-control" rows="2" required>${facilityToEdit.description}</textarea>
                    </div>
                    <div class="row mb-3">
                        <div class="col-md-6">
                            <label class="form-label">Harga Sejam (RM)</label>
                            <input type="number" step="0.01" name="pricePerHour" class="form-control" value="${facilityToEdit.pricePerHour}" required>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Status</label>
                            <select name="status" class="form-control">
                                <option value="Available" ${facilityToEdit.status == 'Available' ? 'selected' : ''}>Available</option>
                                <option value="Maintenance" ${facilityToEdit.status == 'Maintenance' ? 'selected' : ''}>Maintenance</option>
                            </select>
                        </div>
                    </div>
                    <button type="submit" class="btn btn-success">${facilityToEdit != null ? "Simpan Perubahan" : "Tambah"}</button>
                    <c:if class="text-secondary" test="${facilityToEdit != null}">
                        <a href="${pageContext.request.contextPath}/manager/facility" class="btn btn-light">Batal</a>
                    </c:if>
                </form>
            </div>
        </div>

        <!-- Jadual Senarai Fasiliti -->
        <table class="table table-striped table-hover shadow-sm bg-white rounded">
            <thead class="table-dark">
                <tr>
                    <th>Nama</th>
                    <th>Jenis</th>
                    <th>Harga/Jam</th>
                    <th>Status</th>
                    <th>Tindakan</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="f" items="${facilities}">
                    <tr>
                        <td>${f.name}</td>
                        <td>${f.type}</td>
                        <td>RM ${f.pricePerHour}</td>
                        <td><span class="badge ${f.status == 'Available' ? 'bg-success' : 'bg-danger'}">${f.status}</span></td>
                        <td>
                            <a href="${pageContext.request.contextPath}/manager/facility?action=edit&id=${f.id}" class="btn btn-sm btn-warning">Edit</a>
                            <a href="${pageContext.request.contextPath}/manager/facility?action=delete&id=${f.id}" class="btn btn-sm btn-danger" onclick="return confirm('Betul nak padam?')">Padam</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>