<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>

<div class="container">
    <table class="table table-striped">
        <caption>Your Todos are</caption>
        <thead>
            <tr>
                <th>ICMR ID</th>
                <th>LAB Name</th>
                <th>Patient ID</th>
                <th>Patient Name</th>
                <th>Age</th>
                <th>Gender</th>
                <th>District of Residence</th>
                <th>State of Residence</th>
                <th>Address</th>
                <th>Village/Town</th>
                <th>Contact Number</th>
                <th>Confirmation Date</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${records}" var="record">
                <tr>
                    <td>${record.id}</td>
                    <td>${record.labName}</td>
                    <td>${record.patientId}</td>
                    <td>${record.patientName}</td>
                    <td>${record.age}</td>
                    <td>${record.gender}</td>
                    <td>${record.districtResidence}</td>
                    <td>${record.stateResidence}</td>
                    <td>${record.address}</td>
                    <td>${record.villageTown}</td>
                    <td>${record.contactNumber}</td>
                    <td>${record.confirmationDate}</td>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <div>
        <a type="button" class="btn btn-success" href="/add-todo">Add</a>
    </div>
</div>
<%@ include file="common/footer.jspf"%>