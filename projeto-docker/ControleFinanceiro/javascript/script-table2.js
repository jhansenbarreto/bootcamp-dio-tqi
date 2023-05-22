$(document).ready(function () {
    $('#table2').DataTable({
        "ordering": false,
        "info": false,
        "lengthChange": false,
        "pageLength": 7,
        "pagingType": "full_numbers",
        "searching": false,
        "language": {
            "search": "Pesquisar:   ",
            "emptyTable": "Nenhum dado encontrado.",
            "paginate": {
                "first": "Primeira",
                "last": "Última",
                "next": "Próxima",
                "previous": "Anterior"
            }
        }
    });
});